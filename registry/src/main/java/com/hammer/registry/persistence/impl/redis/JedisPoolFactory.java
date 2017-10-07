package com.hammer.registry.persistence.impl.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.*;

/**
 * @Author 桂列华
 * @Date 2017/10/1 21:27.
 * @Email guiliehua@163.com
 */
public class JedisPoolFactory<T> {
    private static Logger logger = LogManager.getLogger(JedisPoolFactory.class);

    private static Pool pool;

    static {
        ResourceBundle customBundle = null;
        ResourceBundle defaultBundle = ResourceBundle.getBundle("default-jedis");
        try {
            customBundle = ResourceBundle.getBundle("jedis");
        }catch (Exception e){
            logger.error("类路径下为找到redis自定义属性文件");
        }

        Integer maxTotal = null;
        if (customBundle.containsKey("redis.pool.maxActive")){
            maxTotal = Integer.valueOf(customBundle.getString("redis.pool.maxActive"));
        }else{
            maxTotal = Integer.valueOf(defaultBundle.getString("redis.pool.maxActive"));
            logger.warn("jedis自定义属性文件未找到【{}】配置项，使用默认值【{}】", "redis.pool.maxActive", maxTotal);
        }

        Integer maxIdle = null;
        if (customBundle.containsKey("redis.pool.maxIdle")){
            maxIdle = Integer.valueOf(customBundle.getString("redis.pool.maxIdle"));
        }else{
            maxIdle = Integer.valueOf(defaultBundle.getString("redis.pool.maxIdle"));
            logger.warn("jedis自定义属性文件未找到【{}】配置项，使用默认值【{}】", "redis.pool.maxIdle", maxIdle);
        }

        Integer maxWaitMillis = null;
        if (customBundle.containsKey("redis.pool.maxWait")){
            maxWaitMillis = Integer.valueOf(customBundle.getString("redis.pool.maxWait"));
        }else{
            maxWaitMillis = Integer.valueOf(defaultBundle.getString("redis.pool.maxWait"));
            logger.warn("jedis自定义属性文件未找到【{}】配置项，使用默认值【{}】", "redis.pool.maxWait", maxWaitMillis);
        }

        Boolean testOnBorrow = null;
        if (customBundle.containsKey("redis.pool.testOnBorrow")){
            testOnBorrow = Boolean.valueOf(customBundle.getString("redis.pool.testOnBorrow"));
        }else{
            testOnBorrow = Boolean.valueOf(defaultBundle.getString("redis.pool.testOnBorrow"));
            logger.warn("jedis自定义属性文件未找到【{}】配置项，使用默认值【{}】", "redis.pool.testOnBorrow", testOnBorrow);
        }

        Boolean testOnReturn = null;
        if (customBundle.containsKey("redis.pool.testOnReturn")){
            testOnReturn = Boolean.valueOf(customBundle.getString("redis.pool.testOnReturn"));
        }else {
            testOnReturn = Boolean.valueOf(defaultBundle.getString("redis.pool.testOnReturn"));
            logger.warn("jedis自定义属性文件未找到【{}】配置项，使用默认值【{}】", "redis.pool.testOnReturn", testOnReturn);
        }

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        List<Map.Entry<String, Integer>> ipPortPairs = getIpPortPairs(customBundle);
        if (ipPortPairs.size() == 1){
            Map.Entry<String, Integer> ipPortPair = ipPortPairs.get(0);
            pool = new JedisPool(ipPortPair.getKey(), ipPortPair.getValue());

        }else if (ipPortPairs.size()>1){
            List<JedisShardInfo> shardInfoList = new LinkedList<JedisShardInfo>();
            for (Map.Entry<String, Integer> ipPortPair : ipPortPairs){
                shardInfoList.add(new JedisShardInfo(ipPortPair.getKey(), ipPortPair.getValue()));
            }
            pool = new ShardedJedisPool(config, shardInfoList);

        }else{
            throw new RuntimeException("IP、port配置信息异常");
        }
    }

    private static List<Map.Entry<String, Integer>> getIpPortPairs(ResourceBundle bundle){
        String ipStr = bundle.getString("redis.ip");
        String portStr = bundle.getString("redis.port");
        String[] ips = ipStr.split(",");
        String[] ports = portStr.split(",");

        if (ips.length != ports.length){
            throw new RuntimeException(String.format("jedis 配置错误，IP和port数量不匹配。已配置IP【%d】个，已配置port【%d】个", ips.length, ports.length));
        }

        int pairCount = ips.length;
        Map<String, Integer> ipPortPairs = new HashMap<String, Integer>(pairCount);
        for (int i=0; i<pairCount; i++){
            ipPortPairs.put(ips[i], Integer.valueOf(ports[i]));
        }

        return new LinkedList<Map.Entry<String, Integer>>(ipPortPairs.entrySet());
    }

    public static <T extends Pool> T getPool(){
        return (T) pool;
    }

}
