package sample;

import org.testng.annotations.Test;
import redis.embedded.RedisServer;

public class EmbeddedRedisTest {
    @Test
    public void testEmbedded() throws Exception {

        RedisServer redisServer = new RedisServer(6379);
        try {
            redisServer.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("=======================");
            e.getCause().printStackTrace();
        }

        redisServer.stop();

    }


    /*

/tmp/1472594360264-0/redis-server-2.8.19: /lib64/libc.so.6: version `GLIBC_2.14' not found (required by /tmp/1472594360264-0/redis-server-2.8.19)
java.lang.RuntimeException: Can't start redis server. Check logs for details.
       	at redis.embedded.AbstractRedisInstance.awaitRedisServerReady(AbstractRedisInstance.java:61)
       	at redis.embedded.AbstractRedisInstance.start(AbstractRedisInstance.java:39)
       	at redis.embedded.RedisServer.start(RedisServer.java:9)

    * */
}
