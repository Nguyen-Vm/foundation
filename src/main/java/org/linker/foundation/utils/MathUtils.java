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

    private MathUtils() {
    }

    /**
     * 返回长度为 length 的随机数
     *
     * @param length
     * @return
     */
    public static String shuffleNum(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = RANDOM.nextInt(10);
            while (i == 0 && num == 0) {
                num = RANDOM.nextInt(10);
            }
            sb.append(num);
        }
        return sb.toString();
    }

    /**
     * val 是否在 from 和 to 之间
     *
     * @param compared
     * @param begin
     * @param end
     * @return
     */
    public static boolean isBetween(final Number compared, final Number begin, final Number end) {
        if (compared == null || begin == null || end == null) {
            return false;
        }
        BigDecimal val = new BigDecimal(String.valueOf(compared));
        BigDecimal from = new BigDecimal(String.valueOf(begin));
        BigDecimal to = new BigDecimal(String.valueOf(end));
        if (from.compareTo(to) > 0) {
            throw new RuntimeException(String.format("begin: %s > end: %s", from, to));
        }
        return val.compareTo(from) >= 0 && val.compareTo(to) <= 0;
    }

    /**
     * 除于,对结果向上取整
     *
     * @param total
     * @param size
     * @return
     */
    public static int ceil(final Long total, final Integer size) {
        if (nvl(size) == 0) {
            throw new ArithmeticException("/ by zero");
        }
        return (int) (Math.ceil(total.doubleValue() / size.doubleValue()));
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
