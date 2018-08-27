package org.linker.foundation.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class PropertiesUtilsTest {

    @Test
    public void getProperty() {
        Assert.assertEquals("20", PropertiesUtils.getProperty("redis.max.total"));
        Assert.assertEquals("20", PropertiesUtils.getProperty("", "20"));
    }
}
