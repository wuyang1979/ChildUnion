package com.qinzi123.happiness.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @title: CalendarUtil
 * @package: com.qinzi123.happiness.util
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public class CalendarUtil {
    private static final String[] WEEK_FORMAT = {"星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六"};

    public static String getWeek(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return WEEK_FORMAT[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public static String getFormatDateTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        return StringUtil.dataFormat(date, FormatConstant.DATETYPE_1);
    }

    public static String getFormatDateTime(long datetime) {
        Date date = new Date(datetime);
        return StringUtil.dataFormat(date, FormatConstant.DATETYPE_1);
    }

    public static String getFormatDateTime(Date date, String format) {
        if (date == null) {
            date = new Date();
        }
        return StringUtil.dataFormat(date, format);
    }

    public static String getFormatDateTime(long datetime, String format) {
        Date date = new Date(datetime);
        return StringUtil.dataFormat(date, format);
    }

    public static long String2Long(String dataStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(
                FormatConstant.DATETIMETYPE_1);
        Date dt2 = null;
        try {
            dt2 = sdf.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long lTime = dt2.getTime();
        return lTime;
    }

    public static boolean isSameDate(long srcDate, long destDate) {
        Calendar srcCalendar = Calendar.getInstance();
        srcCalendar.setTimeInMillis(srcDate);

        Calendar destCalendar = Calendar.getInstance();
        destCalendar.setTimeInMillis(destDate);

        return srcCalendar.get(Calendar.YEAR) == destCalendar
                .get(Calendar.YEAR)
                && srcCalendar.get(Calendar.MONTH) == destCalendar
                .get(Calendar.MONTH)
                && srcCalendar.get(Calendar.DAY_OF_MONTH) == destCalendar
                .get(Calendar.DAY_OF_MONTH);
    }

    public static long getDayBeginTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间对应的0点0分0秒的时间long值
     *
     * @return
     */
    public static long getBeginTimeInOneDay(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * 获取指定时间对应的23点59分59秒的时间long值
     *
     * @return
     */
    public static long getEndTimeInOneDay(long dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTimeInMillis();
    }

    /**
     * 如果是今天，则显示小时分，形如：17:20格式； 否则显示月日，形如：02-12格式
     *
     * @param publishTime
     * @return
     */
    public static String formatPublishTime(long publishTime) {
        // 如果是今天，则显示小时分，形如：17:20格式
        if (CalendarUtil.isSameDate(publishTime, System.currentTimeMillis())) {
            return CalendarUtil.getFormatDateTime(publishTime,
                    FormatConstant.TIMETYPE_2);
        }
        // 否则显示月日，形如：02-12格式
        else {
            return CalendarUtil.getFormatDateTime(publishTime,
                    FormatConstant.DATETYPE_2);
        }
    }

    public static String getDateTime(long time, String timeFormat) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return sdf.format(date);
    }

    public static boolean isSameYear(long srcDate, long destDate) {
        Calendar srcCalendar = Calendar.getInstance();
        srcCalendar.setTimeInMillis(srcDate);

        Calendar destCalendar = Calendar.getInstance();
        destCalendar.setTimeInMillis(destDate);

        return srcCalendar.get(Calendar.YEAR) == destCalendar
                .get(Calendar.YEAR);
    }

    public static boolean isSameDay(long srcDate, long destDate) {
        Calendar srcCalendar = Calendar.getInstance();
        srcCalendar.setTimeInMillis(srcDate);

        Calendar destCalendar = Calendar.getInstance();
        destCalendar.setTimeInMillis(destDate);

        return srcCalendar.get(Calendar.YEAR) == destCalendar
                .get(Calendar.YEAR)
                && srcCalendar.get(Calendar.DAY_OF_YEAR) == destCalendar
                .get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 如果是今年，则显示月日小时分，形如：8月18日 17:20； 否则年月日小时分，形如：2014年8月18日 17:20
     *
     * @param dateTime
     * @return
     */
    public static String formatShowTime(long dateTime) {
        // 如果是同一年的同一天，则显示：今天07:53
        if (isSameDay(dateTime, System.currentTimeMillis())) {
            return getFormatDateTime(dateTime,
                    FormatConstant.DATETIMETYPE_SIMPLE_TODAY);
        }
        // 如果是今年，则显示月日小时分，形如：8月18日 17:20
        else if (isSameYear(dateTime, System.currentTimeMillis())) {
            return getFormatDateTime(dateTime,
                    FormatConstant.DATETIMETYPE_SIMPLE_NOYEAR);
        }
        // 否则显示年月日小时分，形如：2014年8月18日 17:20
        else {
            return getFormatDateTime(dateTime,
                    FormatConstant.DATETIMETYPE_SIMPLE);
        }
    }

    public static String combineShowTime(long beginTime, long endTime) {
        // 如果是同一天，则结束时间不显示日期，仅仅显示时间即可，形如：8月18日 09:00-11:00
        if (isSameDay(beginTime, endTime)) {
            return formatShowTime(beginTime) + "-"
                    + getFormatDateTime(endTime, FormatConstant.TIMETYPE_2);
        } else {
            return formatShowTime(beginTime) + " - " + formatShowTime(endTime);
        }
    }

    public static void main(String[] args) {
        // System.out.println(String2Long("2015-01-30 17:40:00"));
        //
        // System.out.println(String2Long("2015-02-08 09:30:00"));
        //
        // System.out.println(String2Long("2015-01-30 18:00:00"));
        // System.out.println(String2Long("2015-02-06 23:59:59"));
        // System.out.println(StringUtil.dataFormat(new Date(1425866400000L),
        // FormatConstant.DATETIMETYPE_1));
    }
}
