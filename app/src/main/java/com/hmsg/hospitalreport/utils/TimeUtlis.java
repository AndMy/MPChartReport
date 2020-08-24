package com.hmsg.hospitalreport.utils;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;
@SuppressLint("WrongConstant")
public class TimeUtlis {
    /*public static List<String> getWeekByDate(Date date) {
        List<String> dateList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {
            dateList.add(format.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dateList;
    }*/
    //获取当前(上，下)周的日期范围如：...,-1上一周，0本周，1下一周...
    public static List<Long> getWeekDays(int i) {
        List<Long> weeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(/*"yyyy-MM-dd"*/"MM-dd");
        // getTimeInterval(sdf);

        Calendar calendar1 = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayOfWeek) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 获得当前日期是一个星期的第几天
        int day = calendar1.get(Calendar.DAY_OF_WEEK);

        //获取当前日期前（下）x周同星几的日期
        calendar1.add(Calendar.DATE, 7*i);

        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);//周一
        String beginDate = sdf.format(calendar1.getTime());
        weeks.add(calendar1.getTimeInMillis());
        Calendar calendar = Calendar.getInstance();
        for (int j = 1; j < 7; j++) {
            calendar.setTimeInMillis(calendar1.getTimeInMillis());
            calendar.add(Calendar.DATE, j);
            weeks.add(calendar.getTimeInMillis());
        }

        calendar1.add(Calendar.DATE, 6);

        String endDate = sdf.format(calendar1.getTime());

        System.out.println(beginDate+" 到 "+endDate);
        return weeks;
    }
    public static List<Date> getWeekDays1(int i) {
        List<Date> weeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(/*"yyyy-MM-dd"*/"MM-dd");
        // getTimeInterval(sdf);

        Calendar calendar1 = Calendar.getInstance();
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);

        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayOfWeek) {
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 获得当前日期是一个星期的第几天
        int day = calendar1.get(Calendar.DAY_OF_WEEK);

        //获取当前日期前（下）x周同星几的日期
        calendar1.add(Calendar.DATE, 7*i);

        calendar1.add(Calendar.DATE, calendar1.getFirstDayOfWeek() - day);//周一
        String beginDate = sdf.format(calendar1.getTime());
        weeks.add(calendar1.getTime());
        Calendar calendar = Calendar.getInstance();
        for (int j = 1; j < 7; j++) {
            calendar.setTimeInMillis(calendar1.getTimeInMillis());
            calendar.add(Calendar.DATE, j);
            weeks.add(calendar.getTime());
        }

        calendar1.add(Calendar.DATE, 6);

        String endDate = sdf.format(calendar1.getTime());

        System.out.println(beginDate+" 到到到 "+endDate);
        return weeks;
    }
    public static GregorianCalendar[]  getBetweenDate(String d1, String d2) throws ParseException
    {
        Vector<GregorianCalendar> v=new Vector<GregorianCalendar>();
        SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gc1=new GregorianCalendar(),gc2=new GregorianCalendar();
        gc1.setTime(sdf.parse(d1));
        gc2.setTime(sdf.parse(d2));
        do{
            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();
            v.add(gc3);
            gc1.add(Calendar.DAY_OF_MONTH, 1);
        }while(!gc1.after(gc2));
        return v.toArray(new GregorianCalendar[v.size()]);
    }
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
    public static List<Date> getDays(Date start, Date end) {

        // 返回的日期集合
        List<Date> days = new ArrayList<>();

        //SimpleDateFormat dateFormat = new SimpleDateFormat(/*"yyyy-MM-dd"*/"MM-dd");
        try {
            //Date start = dateFormat.parse(startTime);
            //Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            //tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }
    //获取本周的开始时间
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }
    //获取本月的开始时间
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取本月的结束时间
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    //获取上月的开始时间
    public static Date getPreviousBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        return getDayStartTime(calendar.getTime());
    }

    //获取上月的结束时间
    public static Date getPreviousEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);
        return getDayEndTime(calendar.getTime());
    }
    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }
    //获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    //获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        GregorianCalendar[] ga=getBetweenDate("2009-11-29", "2009-12-5");
        for(GregorianCalendar e:ga)
        {
            System.out.println(e.get(Calendar.YEAR)+"年 "+
                    +(e.get(Calendar.MONTH)+1)+"月 "+
                    e.get(Calendar.DAY_OF_MONTH)+"号");
        }
    }
}
