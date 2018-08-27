package org.linker.foundation.utils;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class MathUtilsTest {

    @Test
    public void shuffleNum() {
        for (int i = 0; i < 5; i++) {
            System.out.println(MathUtils.shuffleNum(6));
        }
    }

    @Test
    public void isBetween() {
        Assert.assertTrue(MathUtils.isBetween(3, 1, 5));
    }

    @Test
    public void ceil() {
        Assert.assertEquals(34, MathUtils.ceil(100L, 3));
        Assert.assertEquals(-2, MathUtils.ceil(-9L, 4));
    }

    @Test
    public void nvl() {
        BigDecimal bd = null;
        Long l = null;
        Integer i = null;
        Boolean b = null;
        Assert.assertEquals(BigDecimal.ZERO, MathUtils.nvl(bd));
        Assert.assertEquals(0L, MathUtils.nvl(l));
        Assert.assertEquals(0, MathUtils.nvl(i));
        Assert.assertEquals(false, MathUtils.nvl(b));
    }
}
