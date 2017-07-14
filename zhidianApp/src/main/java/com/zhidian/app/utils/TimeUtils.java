package com.zhidian.app.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zhou on 14-3-14.
 */
public class TimeUtils {
    private static final boolean DBG = true;
    private static final String TAG = TimeUtils.class.getSimpleName();

    /**
     * yyyy-MM-dd HH:mm:ss 转换为秒
     *
     * @param time
     * @return
     */
    public static long time2long(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = null;
        try {
            dt = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt == null ? 0 : dt.getTime() / 1000;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转 yyyy\nMM-dd
     *
     * @param time
     * @return
     */
    public static String timeFormat1(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy\nM-dd");
        long sec = time2long(time);
        Date dt = new Date(sec * 1000);
        return sdf.format(dt);
    }

    public static String timeFormatCustom(String time,String nowFormat, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        long sec = time2long(time);
        Date dt = null;
        try {
            dt = new SimpleDateFormat(nowFormat).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(dt);
    }
    public static String timeFormatCustom(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public static Date timeStr2Date(String time,String format){
        DateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date =  sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int[] getFarDate(String brithday, String nowdate) {
        String[] birth = brithday.split("-");
        String[] mutiDate = nowdate.split("-");

        if (birth.length != 3) {
            return null;
        }
        Calendar birthday = new GregorianCalendar(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]) - 1, Integer.parseInt(birth[2]));
        Calendar now = new GregorianCalendar(Integer.parseInt(mutiDate[0]), Integer.parseInt(mutiDate[1]) - 1, Integer.parseInt(mutiDate[2]));
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        int[] result = new int[3];
        result[0] = year;
        result[1] = month;
        result[2] = day;
        return result;
    }

    public static int[] getCurrentAgeByBirthdate(String brithday) {
        String[] birth = brithday.split("-");
        if (birth.length != 3) {
            return null;
        }
        Calendar birthday = new GregorianCalendar(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]) - 1, Integer.parseInt(birth[2]));
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        int[] result = new int[3];
        result[0] = year;
        result[1] = month;
        result[2] = day;
        return result;
    }

    public static int daysBetween(Date now, Date returnDate) {
        Calendar cNow = Calendar.getInstance();
        Calendar cReturnDate = Calendar.getInstance();
        cNow.setTime(now);
        cReturnDate.setTime(returnDate);
        setTimeToMidnight(cNow);
        setTimeToMidnight(cReturnDate);
        long todayMs = cNow.getTimeInMillis();
        long returnMs = cReturnDate.getTimeInMillis();
        long intervalMs = todayMs - returnMs;
        return millisecondsToDays(intervalMs);
    }

    private static int millisecondsToDays(long intervalMs) {
        return (int) (intervalMs / (1000 * 86400));
    }

    private static void setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    public static void setTimeToMidday(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    public static void setTimeToOnlyHour(Calendar calendar,int hour){
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    /**
     * 是否是正午之后 true 是  false 正午之前
     * @return
     */

    public static boolean afterMidday(){
        Calendar nowCalendar=Calendar.getInstance();
        Calendar nowCalendar1=Calendar.getInstance();
        TimeUtils.setTimeToMidday(nowCalendar1);
        long between=nowCalendar1.getTimeInMillis()-nowCalendar.getTimeInMillis();
        if(between>0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 获取当前时间的字符串
     *
     * @param format
     * @return
     */
    public static String getCurrentTime(String format) {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 获取时间差
     * @param start
     * @param end
     * @return
     */
    public static long getDifference(String start,String end){
        long difference;
        if(TextUtils.isEmpty(start)|| TextUtils.isEmpty(end))
            return 0;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date da1 = null,da2 = null;
        try {
            da1 = mFormat.parse(start);
            da2 = mFormat.parse(end);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        difference = (da2.getTime()-da1.getTime())/(24*60*60*1000);
        return difference;
    }
    public static boolean getCompareTo(String start,String end){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            calendar1.setTime(dateFormat.parse(start));
            calendar2.setTime(dateFormat.parse(end));
        } catch (ParseException e) {

        }
        int result = calendar1.compareTo(calendar2);
        if (result == 0)
            return true;
        else if (result < 0)
            return true;
        else
            return false;
    }

    public static boolean ComparTo(String start,String end){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(start);
            Date dt2 = df.parse(end);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                return true;
            } else {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
    public static String getTimesFormat(String tempTime, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(tempTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
