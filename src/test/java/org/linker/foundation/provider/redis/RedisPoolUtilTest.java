package org.linker.foundation.provider.redis;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class RedisPoolUtilTest {

    @Test
    public void main() {
        RedisPoolUtil.set("Key", "Value");
        Assert.assertEquals("Value", RedisPoolUtil.get("Key"));
        Assert.assertEquals(null, RedisPoolUtil.get(""));

        RedisPoolUtil.set("del", "del");
        RedisPoolUtil.del("del");

        RedisPoolUtil.setEx("nguyen", "nguyen", 1000 * 10);
        RedisPoolUtil.expire("Key", 1000 * 10);
    }
}
