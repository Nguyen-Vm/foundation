package org.linker.foundation.common;

import com.google.common.collect.Lists;
import org.linker.foundation.utils.PropertiesUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.List;

/**
 * @author RWM
 * @date 2018/3/5
 * @description 分布式Redis连接池
 */
public class RedisSharedPool {

    private static ShardedJedisPool pool;

    private static Integer maxTotal = Integer.parseInt(PropertiesUtils.getProperty("redis.max.total", "20"));
    private static Integer maxIdle = Integer.parseInt(PropertiesUtils.getProperty("redis.max.idle", "10"));
    private static Integer minIdle = Integer.parseInt(PropertiesUtils.getProperty("redis.min.idle", "2"));

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtils.getProperty("redis.test.borrow", "true"));
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtils.getProperty("redis.test.return", "false"));

    private static String redisIp1 = PropertiesUtils.getProperty("redis1.ip");
    private static Integer redisPort1 = Integer.parseInt(PropertiesUtils.getProperty("redis1.port"));

    private static String redisIp2 = PropertiesUtils.getProperty("redis2.ip");
    private static Integer redisPort2 = Integer.parseInt(PropertiesUtils.getProperty("redis2.port"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(redisIp1, redisPort1, 2 * 1000);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(redisIp2, redisPort2, 2 * 1000);

        List<JedisShardInfo> jedisShardInfoList = Lists.newArrayList(jedisShardInfo1, jedisShardInfo2);
        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getResource() {
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }

}
