package org.linker.foundation.utils;

import org.apache.commons.lang3.StringUtils;
import org.linker.foundation.dto.DateFormat;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author RWM
 * @date 2018/1/24
 */
public final class DateUtils {
    public static final long MINUTE_TIME = 60 * 1000L;
    public static final long DAY_TIME = 24 * 60 * MINUTE_TIME;
    private static final Date MIN = DateUtils.ofDate("19700101", DateFormat.NumDate);

    private DateUtils() {}

    /**
     * 给定 format 将 date 转换成 string
     * @param date
     * @param format
     * @return
     */
    public static String format(final Date date, DateFormat format) {
        return null == date ? format.name() : new SimpleDateFormat(format.pattern).format(date);
    }

    /**
     * 给定 format 将 localDate 转换成 string
     * @param localDate
     * @param format
     * @return
     */
    public static String format(final LocalDate localDate, DateFormat format) {
        return null == localDate ? format.pattern : localDate.format(DateTimeFormatter.ofPattern(format.pattern));
    }

    /**
     * 给定 format 将 localDateTime 转换成 string
     * @param localDateTime
     * @param format
     * @return
     */
    public static String format(final LocalDateTime localDateTime, DateFormat format) {
        return null == localDateTime ? format.pattern : localDateTime.format(DateTimeFormatter.ofPattern(format.pattern));
    }

    /**
     * 给定 format 将毫秒时间戳 转换成 string
     * @param time
     * @param format
     * @return
     */
    public static String format(long time, DateFormat format) {
        return format(ofLocalDateTime(time), format);
    }

    /**
     * 给定 format 将 dateStr 转换成 Date
     * @param dateStr
     * @param format
     * @return
     */
    public static Date ofDate(String dateStr, DateFormat format) {
        try {
            return new SimpleDateFormat(format.pattern).parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("ofDate error, sDate: " + dateStr + "format: " + format.pattern, e);
        }
    }

    /**
     * 将 毫秒时间戳 转换成 Date
     * @param time
     * @return
     */
    public static Date ofDate(long time) {
        return Date.from(Instant.ofEpochMilli(time));
    }

    /**
     * 给定 format 将 dateStr 转换成 LocalDate
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDate ofLocalDate(String dateStr, DateFormat format) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format.pattern));
    }

    /**
     * 将 毫秒时间戳 转换成 LocalDate
     * @param time
     * @return
     */
    public static LocalDate ofLocalDate(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 给定 format 将 dateStr 转换成 LocalDateTime
     * @param dateStr
     * @param format
     * @return
     */
    public static LocalDateTime ofLocalDateTime(String dateStr, DateFormat format) {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format.pattern));
    }

    /**
     * 将 毫秒时间戳 转换成 LocalDateTime
     * @param time
     * @return
     */
    public static LocalDateTime ofLocalDateTime(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
    }

    /**
     *
     * @return 当前系统时间 Date 类型
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 给定 format 将当前系统时间转换成 string
     * @param format
     * @return
     */
    public static String now(DateFormat format) {
        return format(now(), format);
    }

    /**
     * 当前系统时间秒
     * @return
     */
    public static Long second() {
        return Instant.now().getEpochSecond();
    }

    /**
     * date 时间秒
     * @param date
     * @return
     */
    public static Long second(final Date date) {
        return null == date ? 0L : date.getTime() / 1000;
    }

    /**
     * localDate 时间秒
     * @param localDate
     * @return
     */
    public static Long second(final LocalDate localDate) {
        return null == localDate ? 0L : localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * localDateTime 时间秒
     * @param localDateTime
     * @return
     */
    public static Long second(final LocalDateTime localDateTime) {
        return null == localDateTime ? 0L : localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 当前系统时间 毫秒
     * @return
     */
    public static Long time() {
        return System.currentTimeMillis();
    }

    /**
     * 将 date 转换成 毫秒
     * @param date
     * @return
     */
    public static Long time(final Date date) {
        return null == date ? 0L : date.getTime();
    }

    /**
     * 将localDate 转换成 毫秒
     * @param localDate
     * @return
     */
    public static Long time(final LocalDate localDate) {
        return null == localDate ? 0L : localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 将localDateTime 转换成 毫秒
     * @param localDateTime
     * @return
     */
    public static Long time(final LocalDateTime localDateTime) {
        return null == localDateTime ? 0L : localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 给定的date加days天
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(final Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 星期几
     * @return
     */
    public static int dayOfWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

    /**
     * 星期几
     * @param localDate
     * @return
     */
    public static int dayOfWeek(final LocalDate localDate) {
        return null == localDate ? 0 : localDate.getDayOfWeek().getValue();
    }

    /**
     * 星期几
     * @param date
     * @return
     */
    public static int dayOfWeek(final Date date) {
        return null == date ? 0 : ofLocalDate(date.getTime()).getDayOfWeek().getValue();
    }

    /**
     * 当前时间距离下次某一时刻的时间差，单位:毫秒
     * @param data
     * @param period
     * @return
     */
    public static long timeSlot(final Date data, final Long period) {
        long now = time();
        long time = data.getTime();
        // 给的时间大于当前时间
        if(now < time) {
            // 时间差大于多个执行周期
            if(time - now > period) {
                return (time - now) % period;
            } else {
                return time - now;
            }
        }
        // 给的时间小于当前时间
        else if(now - time >= period) {
            // 时间差大于执行周期
            long slot = (now - time) % period;
            return 0 == slot ? 0 : period - slot;
        }
        else {
            // 时间差小于执行周期
            return period - (now - time);
        }
    }

    /**
     * 当天几点钟时间
     * @param hour
     * @return
     */
    public static Date hourAt(int hour) {
        if (hour < 0 || hour > 23) {
            throw new RuntimeException("hour must between 0 and 23, hour: " + hour);
        }
        return instanceDate(hour, 0, 0);
    }

    /**
     * 当天几点几分时间
     * @param hour
     * @param minute
     * @return
     */
    public static Date minuteAt(int hour, int minute) {
        if (hour < 0 || hour > 23) {
            throw new RuntimeException("hour must between 0 and 23, hour: " + hour);
        }
        if (minute < 0 || minute > 59) {
            throw new RuntimeException("minute must between 0 and 59, minute: " + minute);
        }
        return instanceDate(hour, minute, 0);
    }

    /**
     * 当天几点几分几秒时间
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date secondAt(int hour, int minute, int second) {
        if (hour < 0 || hour > 23) {
            throw new RuntimeException("hour must between 0 and 23, hour: " + hour);
        }
        if (minute < 0 || minute > 59) {
            throw new RuntimeException("minute must between 0 and 59, minute: " + minute);
        }
        if (second >= 0 && second <= 59) {
            throw new RuntimeException("second must between 0 and 59, second: " + second);
        }
        return instanceDate(hour, minute, second);
    }

    private static Date instanceDate(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        return instanceDate(hour, minute, second, calendar);
    }

    private static Date instanceDate(int hour, int minute, int second, Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间相差的天数
     * @param startDate
     * @param endDate
     * @return
     */
    public static int daySize(final Date startDate, final Date endDate) {
        if (startDate == null || endDate == null) {
            return -1;
        }
        long timeMillis = ofDayStart(startDate.getTime()) - ofDayStart(endDate.getTime());
        return BigDecimal.valueOf(timeMillis).divide(BigDecimal.valueOf(DAY_TIME), 0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 给定 毫秒时间戳 得出当天的开始时间毫秒
     * @param time
     * @return
     */
    public static long ofDayStart(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ofDate(time));
        return instanceDate(0, 0, 0, calendar).getTime();
    }

    /**
     * 获取 year,month月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int monthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        // JANUARY -> 0
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取指定时间所在月的开始时间
     * @param date
     * @return
     */
    public static Date firstDayOfMonth(final Date date) {
        if (null == date) {
            return DateUtils.MIN;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在月的结束时间
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(final Date date) {
        if (null == date) {
            return DateUtils.MIN;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // JANUARY -> 0
        calendar.set(Calendar.DATE, monthDays(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1)));
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在周的开始时间
     * @param date
     * @return
     */
    public static Date firstDayOfWeek(final Date date) {
        if (null == date) {
            return DateUtils.MIN;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    /**
     * 获取指定时间所在周的结束时间
     * @param date
     * @return
     */
    public static Date lastDayOfWeek(final Date date) {
        if (null == date) {
            return DateUtils.MIN;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    /**
     * int -> 指定位数的 string
     * @param time
     * @return
     */
    public static String leftPadTime(Integer time) {
        return StringUtils.leftPad(String.valueOf(time), 2, "0");
    }

}
