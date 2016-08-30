package sample;

import org.testng.annotations.Test;
import redis.embedded.RedisServer;

public class EmbeddedRedisTest {
    @Test
    public void testEmbedded() throws Exception {

        RedisServer redisServer = new RedisServer(6379);
        redisServer.start();

        redisServer.stop();

    }
}
