package com.hammer.registry.persistence.impl.redis;

import com.alibaba.fastjson.JSONObject;
import com.hammer.registry.persistence.RegistryService;
import com.hammer.rpc.msg.body.vo.ServiceVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Author 桂列华
 * @Date 2017/10/6 8:33.
 * @Email guiliehua@163.com
 */
@Service
public class RegistryServiceImpl implements RegistryService {
    private static Logger logger = LogManager.getLogger(RegistryServiceImpl.class);

    private static Pool jedisPool = JedisPoolFactory.getPool();

    public void register(ServiceVO serviceVO) {
        Jedis jedis = (Jedis) jedisPool.getResource();
        String body = JSONObject.toJSONString(serviceVO);

        jedis.set(serviceVO.commonServiceGroup() + serviceVO.commonServiceKey(), body);
        logger.info(String.format("registered service:group=%s, key=%s, detail=%s", serviceVO.commonServiceGroup(), serviceVO.commonServiceKey(), body));

        if (serviceVO.isSupportDynamicAlias()){
            jedis.set(serviceVO.dynamicServiceGroup() + serviceVO.dynamicServiceKey(), body);
            logger.info(String.format("registered dynamic service:group=%s, key=%s, detail=%s", serviceVO.dynamicServiceGroup(), serviceVO.dynamicServiceKey(), body));
        }

        jedis.close();
    }

    public void deRegister(ServiceVO serviceVO){
        Jedis jedis = (Jedis) jedisPool.getResource();

        jedis.del(serviceVO.commonServiceGroup() + serviceVO.commonServiceKey());
        logger.info(String.format("deRegistered service:group=%s, key=%s", serviceVO.commonServiceGroup(), serviceVO.commonServiceKey()));

        if (serviceVO.isSupportDynamicAlias()){
            jedis.del(serviceVO.dynamicServiceGroup() + serviceVO.dynamicServiceKey());
            logger.info(String.format("deRegistered dynamic service:group=%s, key=%s", serviceVO.dynamicServiceGroup(), serviceVO.dynamicServiceKey()));
        }

        jedis.close();
    }

    public List<ServiceVO> getByGroup(String group){
        Jedis jedis = (Jedis) jedisPool.getResource();

        Set<String> keys = jedis.keys(group);
        List<ServiceVO> serviceVOList = new LinkedList<ServiceVO>();
        if (keys!=null && !keys.isEmpty()){
            ServiceVO serviceVO = null;
            String body = null;
            for (String key : keys){
                body = jedis.get(key);
                try {
                    serviceVO = JSONObject.parseObject(body, ServiceVO.class);
                    serviceVOList.add(serviceVO);
                }catch (Throwable e){
                    logger.error("traslate service body to ServiceVO.class failed", e);
                }
            }
        }

        return serviceVOList;
    }
}
