package docker;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    @Test
    public void test1() {
        Jedis j = new Jedis("192.168.64.150", 7000);
        j.set("key1", "value1");

        String v = j.get("key1");
        System.out.println(v);

        j.close();
    }
    @Test
    public void test2() {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(500);
        cfg.setMaxIdle(20);

        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("192.168.64.150", 7000));
        shards.add(new JedisShardInfo("192.168.64.150", 7001));
        shards.add(new JedisShardInfo("192.168.64.150", 7002));

        ShardedJedisPool pool = new ShardedJedisPool(cfg, shards);

        ShardedJedis j = pool.getResource();
        for (int i = 0; i < 100; i++) {
            j.set("key"+i, "value"+i);
        }

        pool.close();
    }

}
