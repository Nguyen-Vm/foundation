package org.linker.foundation.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author RWM
 * @date 2018/4/17
 * @description:
 */
public final class MathUtils {
    private static final Random RANDOM = new Random();

    private MathUtils() {}

    /**
     * 返回长度为 length 的随机数
     * @param length
     * @return
     */
    public static String shuffleNum(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * val 是否在 from 和 to 之间
     * @param val
     * @param from
     * @param to
     * @return
     */
    public static boolean isBetween(final Number val, final Number from, final Number to) {
        if (val == null || from == null || to == null) {
            return false;
        }
        BigDecimal valBD = new BigDecimal(String.valueOf(val));
        return valBD.compareTo(new BigDecimal(String.valueOf(from))) >= 0
                && valBD.compareTo(new BigDecimal(String.valueOf(to))) <= 0;
    }

    /**
     * 除于,对结果向上取整
     * @param total
     * @param size
     * @return
     */
    public static int ceil(final Long total, final Integer size) {
        if (nvl(total) <= 0 || nvl(size) <= 0) {
            return 0;
        }
        return (int)(Math.ceil(total.doubleValue() / size.doubleValue()));
    }

    public static BigDecimal nvl(final BigDecimal num) {
        return num == null ? BigDecimal.ZERO : num;
    }

    public static long nvl(final Long num) {
        return num == null ? 0L : num;
    }

    public static int nvl(final Integer num) {
        return num == null ? 0 : num;
    }

    public static boolean nvl(final Boolean bool) {
        return bool == null ? Boolean.FALSE : bool;
    }

}
