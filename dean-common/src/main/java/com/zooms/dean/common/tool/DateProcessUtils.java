package com.zooms.dean.common.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 *
 * @author zlj
 */
public class DateProcessUtils {
    public static final String YMD = "yyyyMMdd";
    public static final String YMD_YEAR = "yyyy";
    public static final String YMD_BREAK = "yyyy-MM-dd";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String YMDHMS_BREAK = "yyyy-MM-dd HH:mm:ss";
    public static final String YMDHMS_BREAK_HALF = "yyyy-MM-dd HH:mm";

    /**
     * 计算时间差
     */
    public static final int CAL_MINUTES = 1000 * 60;
    public static final int CAL_HOURS = 1000 * 60 * 60;
    public static final int CAL_DAYS = 1000 * 60 * 60 * 24;

    /**
     * 获取当前时间格式化后的值
     *
     * @param pattern
     * @return
     */
    public static String getNowDateText(String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 获取日期格式化后的值
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateText(Date date, String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 字符串时间转换成Date格式
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date getDate(String date, String pattern)
            throws ParseException {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取时间戳
     *
     * @param date
     * @return
     */
    public static Long getTime(Date date) {
        return date.getTime();
    }

    /**
     * 计算时间差
     *
     * @param startDate
     * @param endDate
     * @param calType   计算类型,按分钟、小时、天数计算
     * @return
     */
    public static int calDiffs(Date startDate, Date endDate, int calType) {
        Long start = DateProcessUtils.getTime(startDate);
        Long end = DateProcessUtils.getTime(endDate);
        int diff = (int) ((end - start) / calType);
        return diff;
    }

    /**
     * 计算时间差值以某种约定形式显示
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String timeDiffText(Date startDate, Date endDate) {
        int calDiffs = DateProcessUtils.calDiffs(startDate, endDate,
                DateProcessUtils.CAL_MINUTES);
        if (calDiffs == 0) {
            return "刚刚";
        }
        if (calDiffs < 60) {
            return calDiffs + "分钟前";
        }
        calDiffs = DateProcessUtils.calDiffs(startDate, endDate, DateProcessUtils.CAL_HOURS);
        if (calDiffs < 24) {
            return calDiffs + "小时前";
        }
        if (calDiffs < 48) {
            return "昨天";
        }
        return DateProcessUtils.getDateText(startDate, DateProcessUtils.YMDHMS_BREAK_HALF);
    }

    /**
     * 显示某种约定后的时间值,类似微信朋友圈发布说说显示的时间那种
     *
     * @param date
     * @return
     */
    public static String showTimeText(Date date) {
        return DateProcessUtils.timeDiffText(date, new Date());
    }

    public static void main(String[] args) throws ParseException {
        Date start = DateProcessUtils.getDate("2017-09-26 10:54",
                DateProcessUtils.YMDHMS_BREAK_HALF);
        System.out.println(DateProcessUtils.showTimeText(start));
    }

    /**
     * 获取昨天的开始时间
     *
     * @return
     */
    public static Date getYesterdayStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        return time;
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */
    public static Date getYesterdayEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        return time;
    }

    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static Date getCurrentMonthStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        return time;
    }

    /**
     * 获取本月的结束时间
     *
     * @return
     */
    public static Date getCurrentMonthEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, -1);
        Date time = cal.getTime();
        return time;
    }
    /**
     * 时间戳转化为Sting或Date
      */
    public static String formatTimestampToDate(Date date){
        Long timestamp = getTime(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = new Long(timestamp);
        String str = format.format(time);
          return str;

    }
    /**
     * 时间戳转化为Sting或Date
     */
    public static String formatTimestampToDateAndString(Date date,String pattern){
        Long timestamp = getTime(date);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Long time = new Long(timestamp);
        String str = format.format(time);
        return str;

    }
}