import com.hammer.registry.persistence.impl.redis.JedisPoolFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.UnknownHostException;


/**
 * Created by gui on 2017/10/1.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        JedisPool jedisPool = JedisPoolFactory.getPool();
        Jedis jedis = (Jedis)jedisPool.getResource();
        jedis.set("say", "hello");
        logger.info(jedis.get("say"));
        jedisPool.getResource();

    }
}
