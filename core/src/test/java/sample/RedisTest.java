package sample;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.testng.annotations.Test;

public class RedisTest {

    public void testRedis() throws Exception {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommands<String, String> sync = connect.sync();
        String val = sync.get("k1");
        System.out.println(val);
        sync.set("k1", val+"!");

        //how to differentiate domains/users? Hierarchy?
        sync.rpush("requests", val);
        System.out.println(sync.lrange("requests", 0, 10));

        connect.close();
        client.shutdown();

    }
}
