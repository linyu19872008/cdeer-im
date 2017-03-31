package com.cdeer.im.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {


    /**
     * 时间格式化工具(线程局部变量)
     */
    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT = new
            ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf;
                }
            };

    private static ThreadLocal<DateTimeFormatter> dtf = new ThreadLocal<DateTimeFormatter>() {
        @Override
        protected DateTimeFormatter initialValue() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return dateTimeFormatter;
        }
    };

    /**
     * 获取当前时间(秒数)
     *
     * @return
     */
    public static int getNowSecond() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取当前时间(毫秒数)
     *
     * @return
     */
    public static long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前的格式化时间
     *
     * @return
     */
    public static String getFormatTime() {
        return DATE_FORMAT.get().format(new Date());
    }

    /**
     * 获取yyyy-MM-dd HH:mm:ss 格式的日期
     *
     * @return
     */
    public static String getFormatDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(dtf.get());
    }


}
