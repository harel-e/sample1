package sample;

import org.testng.annotations.Test;
import redis.embedded.RedisServer;
import redis.embedded.exceptions.EmbeddedRedisException;

public class EmbeddedRedisTest {
    @Test
    public void testEmbedded() throws Exception {

        RedisServer redisServer = new RedisServer(6379);
        try {
            redisServer.start();
        } catch (EmbeddedRedisException e) {
            e.printStackTrace();
            System.err.println("=======================");
            e.getCause().printStackTrace();
        }

        redisServer.stop();

    }
}
