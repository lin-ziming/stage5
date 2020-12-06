package test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        // 配置对象
        JedisPoolConfig conf = new JedisPoolConfig();

        // 服务器列表
        ArrayList<JedisShardInfo> list = new ArrayList<JedisShardInfo>();
        list.add(new JedisShardInfo("192.168.64.150",7000));
        list.add(new JedisShardInfo("192.168.64.150",7001));
        list.add(new JedisShardInfo("192.168.64.150",7002));

        // 创建分片客户端
        ShardedJedisPool pool = new ShardedJedisPool(conf, list);
        ShardedJedis j = pool.getResource();

        // 存储数据 0-99 键值对
        for (int i = 0; i < 100; i++) {
            j.set("k"+i,"v"+i);
        }
    }
}
