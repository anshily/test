package io.anshily.base.utils;

import jodd.datetime.JDateTime;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 公共方法类
 * </p>
 * <p>
 * 提供有关日期的实用方法集
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: NineTowns
 * </p>
 * <b>作者：</b>兰伟<br>
 * <b>日期：</b>2013-11-05<br>
 *
 * @version 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class DateUtil {
    static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfLongCn = new SimpleDateFormat("yyyy年MM月dd日");
    static SimpleDateFormat sdfShortU = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
    static SimpleDateFormat sdfLongU = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
    static SimpleDateFormat sdfLongTime = new SimpleDateFormat("yyyyMMddHHmmss");
    static SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdfShortLongTimePlusCn = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    static SimpleDateFormat sdfLongTimePlusCn = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    static SimpleDateFormat sdfLongTimePlusMill = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
    static SimpleDateFormat sdfMd = new SimpleDateFormat("MM月dd日");
    private static long DAY_IN_MILLISECOND = 0x5265c00L;

    public DateUtil() {
    }

    /**
     * 获取当前时间戳
     *
     * @return String
     */
    public static String getNowTimestamp() {
        try {
            return String.valueOf(new Date().getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author 兰伟
     */
    public static String getDateLong(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author 兰伟
     */
    public static String getDateLongCn(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLongCn.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author lanwei
     */
    public static String getDateMD(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfMd.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author lanwei
     */
    public static String getDateShortLongTimeCn(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfShortLongTimePlusCn.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * 字符串转化为中文日期
     *
     * @return String
     * @throws Exception
     * @author
     */
    public static String getDateLongTimeCn(String dateStr) {
        String nowDate = "";
        try {
            if (dateStr != null) {
                Date date = getDateFromString(dateStr);
                nowDate = sdfLongTimePlusCn.format(date);
            } else {
                nowDate = dateStr;
            }
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author lanwei
     */
    public static String getDateUS(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfLongU.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * @return String
     * @throws Exception
     * @author lanwei
     */
    public static String getDateUSShort(Date date) {
        String nowDate = "";
        try {
            if (date != null)
                nowDate = sdfShortU.format(date);
            return nowDate;
        } catch (Exception e) {
            System.out.println("Error at getDate:" + e.getMessage());
            return "";
        }
    }

    /**
     * 简单转换日期类型到字符串类型，本地信息设为UK
     *
     * @param date
     * @param format
     * @return String
     */
    public static String getFomartDate(Date date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.UK).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return (date == null) ? new Date().toString() : date.toString();
        }
    }

    /**
     * Descrption:取得当前日期时间,格式为:YYYYMMDDHHMISS
     *
     * @return String
     * @throws Exception
     */
    public static String getNowLongTime() throws Exception {
        String nowTime = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowTime = sdfLongTime.format(date);
            return nowTime;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descrption:取得当前日期,格式为:YYYYMMDD
     *
     * @return String
     * @throws Exception
     */
    public static String getNowShortDate() throws Exception {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = sdfShort.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descrption:取得当前日期,格式为:YYYY-MM-DD
     *
     * @return String
     * @throws Exception
     */
    public static String getNowFormateDate() throws Exception {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = sdfLong.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getNowPlusTime() throws Exception {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getPlusTime(Date date) throws Exception {
        if (date == null)
            return null;
        try {
            String nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
     *
     * @return String
     * @throws Exception
     */
    public static String getPlusTime2(Date date) {

        if (date == null)
            return null;
        try {
            String nowDate = sdfLongTimePlus.format(date);
            return nowDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Descrption:取得当前日期到毫秒极,格式为:yyyyMMddHHmmssSSSS
     *
     * @return String
     * @throws Exception
     */
    public static String getNowPlusTimeMill() throws Exception {
        String nowDate = "";
        try {
            java.sql.Date date = null;
            date = new java.sql.Date(new Date().getTime());
            nowDate = sdfLongTimePlusMill.format(date);
            return nowDate;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到当前年份值:1900
     *
     * @return String
     * @throws Exception
     */
    public static String getNowYear() throws Exception {
        String nowYear = "";
        try {
            String strTemp = getNowLongTime();
            nowYear = strTemp.substring(0, 4);
            return nowYear;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到当前月份值:12
     *
     * @return String
     * @throws Exception
     */
    public static String getNowMonth() throws Exception {
        String nowMonth = "";
        try {
            String strTemp = getNowLongTime();
            nowMonth = strTemp.substring(4, 6);
            return nowMonth;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到当前日期值:30
     *
     * @return String
     * @throws Exception
     */
    public static String getNowDay() throws Exception {
        String nowDay = "";
        try {
            String strTemp = getNowLongTime();
            nowDay = strTemp.substring(6, 8);
            return nowDay;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到当前小时值:23
     *
     * @return String
     * @throws Exception
     */
    public static String getNowHour() throws Exception {
        String nowHour = "";
        try {
            String strTemp = getNowPlusTimeMill();
            nowHour = strTemp.substring(8, 10);
            return nowHour;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据秒数返回时分秒
     *
     * @param _second 秒数
     * @return String
     * @throws Exception
     */
    public static String getTimeBySecond(String _second) throws Exception {
        String returnTime = "";
        long longHour = 0;
        long longMinu = 0;
        long longSec = 0;
        try {
            longSec = Long.parseLong(_second);
            if (longSec == 0) {
                returnTime = "0时0分0秒";
                return returnTime;
            }
            longHour = longSec / 3600; // 取得小时数
            longSec = longSec % 3600; // 取得余下的秒
            longMinu = longSec / 60; // 取得分数
            longSec = longSec % 60; // 取得余下的秒
            returnTime = longHour + "时" + longMinu + "分" + longSec + "秒";
            return returnTime;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * pablo 根据毫秒数返回时分秒毫秒
     *
     * @param ms_second 秒数
     * @return String
     * @throws Exception
     */
    public static String getTimeBySecond(long ms_second) throws Exception {
        String returnTime = "";
        long longHour = 0;
        long longMinu = 0;
        long longSec = 0;
        long longMs = ms_second;
        try {
            if (longMs == 0) {
                returnTime = "0时0分0秒0毫秒";
                return returnTime;
            }
            longHour = longMs / 3600000; // 取得小时数
            longMs = longMs % 3600000; // 取得余下的毫秒
            longMinu = longMs / 60000; // 取得分数
            longMs = longMs % 60000; // 取得余下的毫秒
            longSec = longMs / 1000; // 取得余下的秒
            longMs = longMs % 1000; // 取得余下的毫秒
            returnTime = longHour + "时" + longMinu + "分" + longSec + "秒" + longMs + "毫秒";
            return returnTime;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到日期中的年份
     *
     * @param date 日期
     * @return yyyy格式的年份
     */
    public static int convertDateToYear(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy", new DateFormatSymbols());
        return Integer.parseInt(df.format(date));
    }

    /**
     * 得到日期中年月组成的字符串
     *
     * @param d 日期
     * @return yyyyMM格式的年月字符串
     */
    public static String convertDateToYearMonth(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 得到日期中年月日组成的字符串
     *
     * @param d 日期
     * @return yyyyMMdd格式的年月日字符串
     */
    public static String convertDateToYearMonthDay(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 得到日期中的月份
     *
     * @param d 日期
     * @return yyyy格式的年份
     */
    public static String convertDateToMonth(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("MM", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 得到日期中的日
     *
     * @param d 日期
     * @return yyyy格式的年份
     */
    public static String convertDateToDay(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("dd", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 得到日期中的小时
     *
     * @param d 日期
     * @return HH格式的小时
     */
    public static String convertDateToHour(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("HH", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 得到日期中的分钟
     *
     * @param d 日期
     * @return mm格式的分钟
     */
    public static String convertDateToMinute(Date d) {
        SimpleDateFormat df = new SimpleDateFormat("mm", new DateFormatSymbols());
        return df.format(d);
    }

    /**
     * 获取当前日期为日期型
     *
     * @return 当前日期，java.util.Date类型
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();

        // String currentDate = null;
        Date d = cal.getTime();

        return d;
    }

    /**
     * 获取当前年月的字符串
     *
     * @return 当前年月，yyyyMM格式
     */
    public static String getCurrentYearMonth() {
        Calendar cal = Calendar.getInstance();
        String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
        String currentMonth = null;
        if (cal.get(Calendar.MONTH) < 9)
            currentMonth = "0" + (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
        else
            currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
        return (currentYear + currentMonth);
    }

    /**
     * 获取当前年为整型
     *
     * @return 获取当前日期中的年，int型
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear;
    }

    /**
     * 将指定格式的字符串转换为日期型
     *
     * @param strDate      - 日期
     * @param oracleFormat --oracle型日期格式
     * @return 转换得到的日期
     */
    public static Date stringToDate(String strDate, String oracleFormat) {
        if (strDate == null)
            return null;
        Hashtable h = new Hashtable();
        String javaFormat = new String();
        String s = oracleFormat.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");

        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");

        int intStart = 0;
        while (s.indexOf("-", intStart) != -1) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf("/", intStart) != -1) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(" ", intStart) != -1) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(":", intStart) != -1) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
            intStart++;
        }

        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");

        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));

            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat);

        Date myDate = new Date();
        try {
            myDate = df.parse(strDate);
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }

        return myDate;
    }

    /**
     * 获取输入格式的日期字符串，字符串遵循Oracle格式
     *
     * @param d      - 日期
     * @param format - 指定日期格式，格式的写法为Oracle格式
     * @return 按指定的日期格式转换后的日期字符串
     */
    public static String dateToString(Date d, String format) {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = new String();
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");

        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");

        int intStart = 0;
        while (s.indexOf("-", intStart) != -1) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf("/", intStart) != -1) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(" ", intStart) != -1) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(":", intStart) != -1) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
            intStart++;
        }

        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");

        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));

            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());

        return df.format(d);
    }

    /**
     * 获取输入格式的日期字符串，字符串遵循Oracle格式
     *
     * @param d      - 日期
     * @param format - 指定日期格式，格式的写法为Oracle格式
     * @return 按指定的日期格式转换后的日期字符串
     */
    public static String getDate(Date d, String format) {
        if (d == null)
            return "";
        Hashtable h = new Hashtable();
        String javaFormat = new String();
        String s = format.toLowerCase();
        if (s.indexOf("yyyy") != -1)
            h.put(new Integer(s.indexOf("yyyy")), "yyyy");
        else if (s.indexOf("yy") != -1)
            h.put(new Integer(s.indexOf("yy")), "yy");
        if (s.indexOf("mm") != -1)
            h.put(new Integer(s.indexOf("mm")), "MM");

        if (s.indexOf("dd") != -1)
            h.put(new Integer(s.indexOf("dd")), "dd");
        if (s.indexOf("hh24") != -1)
            h.put(new Integer(s.indexOf("hh24")), "HH");
        if (s.indexOf("mi") != -1)
            h.put(new Integer(s.indexOf("mi")), "mm");
        if (s.indexOf("ss") != -1)
            h.put(new Integer(s.indexOf("ss")), "ss");

        int intStart = 0;
        while (s.indexOf("-", intStart) != -1) {
            intStart = s.indexOf("-", intStart);
            h.put(new Integer(intStart), "-");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf("/", intStart) != -1) {
            intStart = s.indexOf("/", intStart);
            h.put(new Integer(intStart), "/");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(" ", intStart) != -1) {
            intStart = s.indexOf(" ", intStart);
            h.put(new Integer(intStart), " ");
            intStart++;
        }

        intStart = 0;
        while (s.indexOf(":", intStart) != -1) {
            intStart = s.indexOf(":", intStart);
            h.put(new Integer(intStart), ":");
            intStart++;
        }

        if (s.indexOf("年") != -1)
            h.put(new Integer(s.indexOf("年")), "年");
        if (s.indexOf("月") != -1)
            h.put(new Integer(s.indexOf("月")), "月");
        if (s.indexOf("日") != -1)
            h.put(new Integer(s.indexOf("日")), "日");
        if (s.indexOf("时") != -1)
            h.put(new Integer(s.indexOf("时")), "时");
        if (s.indexOf("分") != -1)
            h.put(new Integer(s.indexOf("分")), "分");
        if (s.indexOf("秒") != -1)
            h.put(new Integer(s.indexOf("秒")), "秒");

        int i = 0;
        while (h.size() != 0) {
            Enumeration e = h.keys();
            int n = 0;
            while (e.hasMoreElements()) {
                i = ((Integer) e.nextElement()).intValue();
                if (i >= n)
                    n = i;
            }
            String temp = (String) h.get(new Integer(n));
            h.remove(new Integer(n));

            javaFormat = temp + javaFormat;
        }
        SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());

        return df.format(d);
    }

    /**
     * 根据身份证号码获取年龄
     *
     * @param id 身份证号
     * @return int - 年龄
     * @throws Exception 身份证号错误时发生
     */
    public static int getAge(String id) throws Exception {
        int age = -1;
        int length = id.length();
        String birthday = "";
        if (length == 15) {
            birthday = id.substring(6, 8);
            birthday = "19" + birthday;
        } else if (length == 18) {
            birthday = id.substring(6, 10);
        } else {
            throw new Exception("错误的身份证号");
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        age = currentYear - (new Integer(birthday)).intValue();
        return age;
    }

    /**
     * 根据年龄获取出生年份
     *
     * @param age int 年龄
     * @return Date - 出生年份
     */
    public static java.sql.Date getDateByAge(int age) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        // long current = calendar.getTimeInMillis();
        calendar.set(calendar.get(Calendar.YEAR) - age, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        return new java.sql.Date((calendar.getTimeInMillis()));
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param birthday
     * @return
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 比较两个日期(年月型，格式为YYYYMM)之间相差月份
     *
     * @param dealMonth  - 开始年月
     * @param alterMonth - 结束年月
     * @return alterMonth-dealMonth相差的月数
     */
    public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
        int length = 0;
        if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
            // 比较年月字符串的长度不正确
            length = -1;

        } else {
            int dealInt = Integer.parseInt(dealMonth);
            int alterInt = Integer.parseInt(alterMonth);
            if (dealInt < alterInt) {
                // 第一个年月变量应大于或等于第二个年月变量
                length = -2;
            } else {
                int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
                int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
                int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
                int alterMonthInt = Integer.parseInt(alterMonth.substring(4, 6));
                length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
            }
        }

        return length;
    }

    /**
     * 得到两个日期之间相差的天数
     *
     * @param newDate 大的日期
     * @param oldDate 小的日期
     * @return newDate-oldDate相差的天数
     */
    public static int daysBetweenDates(Date newDate, Date oldDate) {
        int days = 0;
        Calendar calo = Calendar.getInstance();
        Calendar caln = Calendar.getInstance();
        calo.setTime(oldDate);
        caln.setTime(newDate);
        int oday = calo.get(Calendar.DAY_OF_YEAR);
        int nyear = caln.get(Calendar.YEAR);
        int oyear = calo.get(Calendar.YEAR);
        while (nyear > oyear) {
            calo.set(Calendar.MONTH, 11);
            calo.set(Calendar.DATE, 31);
            days = days + calo.get(Calendar.DAY_OF_YEAR);
            oyear = oyear + 1;
            calo.set(Calendar.YEAR, oyear);
        }
        int nday = caln.get(Calendar.DAY_OF_YEAR);
        days = days + nday - oday;

        return days;
    }

    /**
     * 取得与原日期相差一定天数的日期，返回Date型日期
     *
     * @param date       原日期
     * @param intBetween 相差的天数
     * @return date加上intBetween天后的日期
     */
    public static Date getDateBetween(Date date, int intBetween) {
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.DATE, intBetween);
        return calo.getTime();
    }

    /**
     * 按指定格式取得与原日期相差一定天数的日期，返回String型日期
     *
     * @param date       原日期
     * @param intBetween 相差的日期
     * @param strFromat  返回日期的格式
     * @return date加上intBetween天后的日期
     */
    public static String getDateBetween_String(Date date, int intBetween, String strFromat) {
        Date dateOld = getDateBetween(date, intBetween);
        return getDate(dateOld, strFromat);
    }

    /**
     * 得到将年月型字符串增加1月后的日期字符串
     *
     * @param yearMonth yyyyMM格式
     * @return yearMonth增加一个月后的日期，yyyyMM格式
     */
    public static String increaseYearMonth(String yearMonth) {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        month = month + 1;
        if (month <= 12 && month >= 10)
            return yearMonth.substring(0, 4) + (new Integer(month)).toString();
        else if (month < 10)
            return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
        else
            // if(month>12)
            return (new Integer(year + 1)).toString() + "0" + (new Integer(month - 12)).toString();

    }

    /**
     * 得到将年月型字符串增加指定月数后的日期字符串
     *
     * @param yearMonth yyyyMM格式日期
     * @param addMonth  增加指定月数
     * @return yearMonth 增加addMonth个月后的日期，yyyyMM格式
     */
    public static String increaseYearMonth(String yearMonth, int addMonth) {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        month = month + addMonth;
        year = year + month / 12;
        month = month % 12;
        if (month <= 12 && month >= 10)
            return year + (new Integer(month)).toString();
        else
            return year + "0" + (new Integer(month)).toString();

    }

    /**
     * 得到将年月型字符串减去1月后的日期字符串
     *
     * @param yearMonth - yyyyMM格式
     * @return - yearMonth减少一个月的日期，yyyyMM格式
     */
    public static String descreaseYearMonth(String yearMonth) {
        int year = (new Integer(yearMonth.substring(0, 4))).intValue();
        int month = (new Integer(yearMonth.substring(4, 6))).intValue();
        month = month - 1;
        if (month >= 10)
            return yearMonth.substring(0, 4) + (new Integer(month)).toString();
        else if (month > 0 && month < 10)
            return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
        else
            // if(month>12)
            return (new Integer(year - 1)).toString() + (new Integer(month + 12)).toString();

    }

    /**
     * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
     * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
     *
     * @param s1 日期1
     * @param s2 日期2
     * @return boolean 如果s1大于等于s2则返回真，否则返回假
     */
    public static boolean yearMonthGreatEqual(String s1, String s2) {
        String temp1 = s1.substring(0, 4);
        String temp2 = s2.substring(0, 4);
        String temp3 = s1.substring(4, 6);
        String temp4 = s2.substring(4, 6);

        if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
            return true;
        else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
            if (Integer.parseInt(temp3) >= Integer.parseInt(temp4))
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
     * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
     *
     * @param s1 日期1
     * @param s2 日期2
     * @return boolean 如果s1大于s2则返回真，否则返回假
     */
    public static boolean yearMonthGreater(String s1, String s2) {
        String temp1 = s1.substring(0, 4);
        String temp2 = s2.substring(0, 4);
        String temp3 = s1.substring(4, 6);
        String temp4 = s2.substring(4, 6);

        if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
            return true;
        else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
            if (Integer.parseInt(temp3) > Integer.parseInt(temp4))
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * 将日期型数据转换成Oracle要求的标准格式的字符串
     *
     * @param date 日期
     * @return 格式化后的字符串
     */
    public static String getOracleFormatDateStr(Date date) {
        return getDate(date, "YYYY-MM-DD HH24:MI:SS");
    }

    /**
     * 字串6位，前4代表年，后2代表月， 返回给定日期中的月份中的最后一天 param term(YYYYMMDD)
     *
     * @param term - 年月，格式为yyyyMM
     * @return String 指定年月中该月份的天数
     */
    public static String getLastDay(String term) {

        int getYear = Integer.parseInt(term.substring(0, 4));
        int getMonth = Integer.parseInt(term.substring(4, 6));

        String getLastDay = "";

        if (getMonth == 2) {
            if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0) {
                getLastDay = "29";
            } else {
                getLastDay = "28";
            }
        } else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11) {
            getLastDay = "30";
        } else {
            getLastDay = "31";
        }
        return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月" + getLastDay + "日";
    }

    /**
     * 返回两个年月(例如：200206)之间相差的月数，年月格式为yyyyMM
     *
     * @param strDateBegin - String
     * @param strDateEnd   String
     * @return String strDateEnd-strDateBegin相差的月数
     */
    public static String getMonthBetween(String strDateBegin, String strDateEnd) {
        try {
            int intMonthBegin;
            int intMonthEnd;
            String strOut;
            if (strDateBegin.equals("") || strDateEnd.equals("") || strDateBegin.length() != 6 || strDateEnd.length() != 6)
                strOut = "";
            else {
                intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4)) * 12 + Integer.parseInt(strDateBegin.substring(4, 6));
                intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12 + Integer.parseInt(strDateEnd.substring(4, 6));
                strOut = String.valueOf(intMonthBegin - intMonthEnd);
            }
            return strOut;
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 将yyyyMMDD格式的日期转换为yyyy-MM-DD格式的日期 返回带'-'的日期(例如：20020612 转换为 2002-06-12)
     *
     * @param strDate String yyyyMMDD格式的日期
     * @return String - yyyy-MM-DD格式的日期
     */
    public static String getStrHaveAcross(String strDate) {
        try {
            return strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
        } catch (Exception e) {
            return strDate;
        }
    }

    /**
     * 取得当前日期的下一个月的第一天
     *
     * @return 当前日期的下个月的第一天，格式为yyyyMMDD
     */
    public static String getFirstDayOfNextMonth() {
        try {
            return increaseYearMonth(getNowShortDate().substring(0, 6)) + "01";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 取得当前日期的下一个月的第一天
     *
     * @return 当前日期的下个月的第一天，格式为yyyyMMDD
     */
    public static String getFirstDayOfThisMonth() {
        try {
            return getNowShortDate().substring(0, 6) + "01";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将yyyyMM各式转换成yyyy年MM月格式
     *
     * @param yearMonth 年月类型的字符串
     * @return String
     */
    public static String getYearAndMonth(String yearMonth) {
        if (null == yearMonth)
            return "";
        String ym = yearMonth.trim();
        if (6 != ym.length())
            return ym;
        String year = ym.substring(0, 4);
        String month = ym.substring(4);
        return new StringBuffer(year).append("年").append(month).append("月").toString();
    }

    /**
     * 将输入的Integer类型的月数转化成"X年X月"格式的字符串
     *
     * @param month Integer
     * @return String
     */
    public static String month2YearMonth(String month) {
        String yearMonth = "";
        int smonth = 0;
        int year = 0;
        int rmonth = 0;

        if ((null == month) || ("0".equals(month)) || "".equals(month.trim())) {
            return "0月";
        }

        smonth = Integer.parseInt(month);
        year = smonth / 12;
        rmonth = smonth % 12;

        if (year > 0) {
            yearMonth = year + "年";
        }
        if (rmonth > 0) {
            yearMonth += rmonth + "个月";
        }

        return yearMonth;
    }

    /**
     * 将yyyyMM各式转换成yyyy年MM月格式
     *
     * @param month 月
     * @return 返回年月型格式的日期
     */
    public static String getYearMonthByMonth(String month) {
        if (null == month)
            return null;
        String ym = month.trim();
        if (6 != ym.length())
            return ym;
        String year = ym.substring(0, 4);
        // String month1 = ym.substring(4);
        return new StringBuffer(year).append("年").append(month).append("月").toString();
    }

    /**
     * 得到将date增加指定月数后的date
     *
     * @param date       日期
     * @param intBetween 增加的月份
     * @return date 加上intBetween月数后的日期
     */
    public static Date increaseMonth(Date date, int intBetween) {
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.MONTH, intBetween);
        return calo.getTime();
    }

    /**
     * 得到将date增加指定天数后的date
     *
     * @param date       日期
     * @param intBetween 增加的天数
     * @return date 加上intBetween天数后的日期
     */
    public static Date increaseDay(Date date, int intBetween) {
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.DATE, intBetween);
        return calo.getTime();
    }

    /**
     * 得到将date增加指定年数后的date
     *
     * @param date       日期
     * @param intBetween 增加的年数
     * @return date加上intBetween年数后的日期
     */
    public static Date increaseYear(Date date, int intBetween) {
        Calendar calo = Calendar.getInstance();
        calo.setTime(date);
        calo.add(Calendar.YEAR, intBetween);
        return calo.getTime();
    }

    /**
     * 比较两个时间先后
     *
     * @param str1 传入的字符串
     * @param str2 传入的字符串
     * @return int negative integer, zero, or a positive integer as str1 is less
     * than, equal to, or greater than str2
     */
    public static int compareDate(String str1, String str2) {
        Date date1 = null, date2 = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            date1 = formatter.parse(str1);
            date2 = formatter.parse(str2);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        if (date1 == null || date2 == null) {

            return -1000000;
        }

        return date1.compareTo(date2);

    }

    public static int compareDate(String str1, Date date2) {
        Date date1 = getDateByString(str1);
        return date1.compareTo(date2);
    }

    public static int compareDate(String format, String str1, Date date2) {

        Date date1 = null;
        try {
            date1 = fromStringToDate(format, str1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.compareTo(date2);
    }

    /**
     * 根据传入的日期字符串转换成相应的日期对象，如果字符串为空或不符合日期格 式，则返回当前时间。
     *
     * @param strDate String 日期字符串
     * @return java.sql.Timestamp 日期对象
     */
    public static Timestamp getDateByString(String strDate) {
        if (strDate == null || strDate.trim().equals("")) {
            return new Timestamp(System.currentTimeMillis());
        }
        try {
            strDate = getFormattedDate(strDate, "yyyy-MM-dd HH:mm:ss") + ".000000000";
            return Timestamp.valueOf(strDate);
        } catch (Exception ex) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    public static Timestamp getNextMonyDate(String strDate) {
        try {
            int iYear = CheckStringUtil.getStrToInt(getFormattedDate(strDate, "yyyy"));
            int iMonth = CheckStringUtil.getStrToInt(getFormattedDate(strDate, "MM"));
            if (iMonth == 12) {
                iYear = iYear + 1;
            } else {
                iMonth = iMonth + 1;
            }
            String strMonth = Integer.toString(iMonth);
            if (strMonth.length() == 1) {
                strMonth = "0" + strMonth;
            }
            strDate = Integer.toString(iYear) + "/" + strMonth + "/01";
            return getDateByString(strDate);
        } catch (Exception ex) {
            return getDateByString(strDate);
        }
    }

    /**
     * 根据参数名称，从request对象中取出该参数，并把该参数转换成GB2312编码的字符集。
     *
     * @param request      请求对象
     * @param strParamName 参数名称
     * @return java.sql.Date 转换后的参数值
     */
    public static Timestamp getDateFromReqParam(HttpServletRequest request, String strParamName) {
        String strStr = CheckStringUtil.getNotNullStr(request.getParameter(strParamName));
        strStr = CheckStringUtil.returnStr(strStr);
        return getDateByString(strStr);
    }

    /**
     * 得到当前日期，格式yyyy-MM-dd。
     *
     * @return String 格式化的日期字符串
     */
    public static String getCurrDate() {
        return getFormattedDate(getDateByString(""));
    }

    /**
     * 得到当前日期，格式yyyy-MM-dd。
     *
     * @return String 格式化的日期字符串
     */
    public static String getToday() {
        Date cDate = new Date();
        SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return cSimpleDateFormat.format(cDate);
    }

    /**
     * 得到当前日期，格式yyyy-MM-dd。
     *
     * @return String 格式化的日期字符串
     */
    public static String getYesterday() {
        Date cDate = new Date();
        cDate.setTime(cDate.getTime() - 24 * 3600 * 1000);
        SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return cSimpleDateFormat.format(cDate);
    }

    /**
     * 得到当前日期，格式yyyy-MM-dd。
     *
     * @return String 格式化的日期字符串
     */
    public static String getTomorrow() {
        Date cDate = new Date();
        cDate.setTime(cDate.getTime() + 24 * 3600 * 1000);
        SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return cSimpleDateFormat.format(cDate);
    }

    /**
     * 返回默认的功能生效的时间，1900/01/01。
     *
     * @return String 默认的实效时间字符串
     */
    public static String getDefaultValidDate() {
        return "1900-01-01";
    }

    /**
     * 返回默认的功能失效的时间，2099/12/31。
     *
     * @return String 默认的实效时间字符串
     */
    public static String getDefaultExpireDate() {
        return "2099-12-31";
    }

    /**
     * 得到当前日期时间,格式为yyyy-MM-dd hh:mm:ss.
     *
     * @return String
     */
    public static String getCurrDateTime() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * 得到指定的日期，如一年三个月零九天后(以yyyy/MM/dd格式显示)参数为("yyyy/MM/dd",1,3,9)
     *
     * @param strFormat strFormat
     * @param iYear     iYear
     * @param iMonth    iMonth
     * @param iDate     iDate
     * @return String
     */
    public static String getSpecDate(String strFormat, int iYear, int iMonth, int iDate) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(Calendar.YEAR, rightNow.get(Calendar.YEAR) + iYear);
        rightNow.set(Calendar.MONTH, rightNow.get(Calendar.MONTH) + iMonth);
        rightNow.set(Calendar.DATE, rightNow.get(Calendar.DATE) + iDate);
        SimpleDateFormat df = new SimpleDateFormat(strFormat);
        return df.format(rightNow.getTime());
    }

    /**
     * 对输入的日期字符串进行默认的格式yyyy-MM-dd HH:mm:ss转换。
     *
     * @param strDate String 需要进行格式化的日期字符串
     * @return String 经过格式化后的字符串
     */
    public static String getDefaultFormattedDate(String strDate) {
        return getFormattedDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 对输入的日期进行默认的格式yyyy-MM-dd HH:mm:ss转换。
     *
     * @param dtDate 需要进行格式化的日期
     * @return String 经过格式化后的字符串
     */
    public static String getDefaultFormattedDate(Timestamp dtDate) {
        return getFormattedDate(dtDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp getNullBirthDay() {
        return new Timestamp(0);
    }

    /**
     * 对输入的日期字符串按照默认的格式yyyy-MM-dd转换.
     *
     * @param strDate String 需要进行格式化的日期字符串
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(String strDate) {
        return getFormattedDate(strDate, "yyyy-MM-dd");
    }

    /**
     * 对输入的日期字符串进行格式化,如果输入的是0000/00/00 00:00:00则返回空串.
     *
     * @param strDate     String 需要进行格式化的日期字符串
     * @param strFormatTo String 要转换的日期格式
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(String strDate, String strFormatTo) {
        if (strDate == null || strDate.trim().equals("")) {
            return "";
        }
        strDate = strDate.replace('/', '-');
        strFormatTo = strFormatTo.replace('/', '-');
        if (strDate.equals("0000-00-00 00:00:00") || strDate.equals("1800-01-01 00:00:00")) {
            return "";
        }
        String formatStr = strFormatTo; // "yyyyMMdd";
        if (strDate == null || strDate.trim().equals("")) {
            return "";
        }
        switch (strDate.trim().length()) {
            case 6:
                if (strDate.substring(0, 1).equals("0")) {
                    formatStr = "yyMMdd";
                } else {
                    formatStr = "yyyyMM";
                }
                break;
            case 8:
                formatStr = "yyyyMMdd";
                break;
            case 10:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd";
                } else {
                    formatStr = "yyyy-MM-dd";
                }
                break;
            case 11:
                if (strDate.getBytes().length == 14) {
                    formatStr = "yyyy年MM月dd日";
                } else {
                    return "";
                }
            case 14:
                formatStr = "yyyyMMddHHmmss";
                break;
            case 19:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd HH:mm:ss";
                } else {
                    formatStr = "yyyy-MM-dd HH:mm:ss";
                }
                break;
            case 21:
                if (strDate.indexOf("-") == -1) {
                    formatStr = "yyyy/MM/dd HH:mm:ss.S";
                } else {
                    formatStr = "yyyy-MM-dd HH:mm:ss.S";
                }
                break;
            default:
                return strDate.trim();
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(strDate));
            formatter = new SimpleDateFormat(strFormatTo);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            // Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
            return "";
        }
    }

    /**
     * 对输入的日期按照默认的格式yyyy-MM-dd转换.
     *
     * @param dtDate 需要进行格式化的日期字符串
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(Timestamp dtDate) {
        return getFormattedDate(dtDate, "yyyy-MM-dd");
    }

    /**
     * 对输入的日期进行格式化, 如果输入的日期是null则返回空串.
     *
     * @param dtDate      java.sql.Timestamp 需要进行格式化的日期字符串
     * @param strFormatTo String 要转换的日期格式
     * @return String 经过格式化后的字符串
     */
    public static String getFormattedDate(Timestamp dtDate, String strFormatTo) {
        if (dtDate == null) {
            return "";
        }
        if (dtDate.equals(new Timestamp(0))) {
            return "";
        }
        strFormatTo = strFormatTo.replace('/', '-');
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            if (Integer.parseInt(formatter.format(dtDate)) < 1900) {
                return "";
            } else {
                formatter = new SimpleDateFormat(strFormatTo);
                return formatter.format(dtDate);
            }
        } catch (Exception e) {
            // Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
            return "";
        }
    }

    /**
     * 把秒数转换成hh:mm:ss格式
     *
     * @param lSecond long
     * @return String
     */
    public static String getTimeFormat(long lSecond) {
        String szTime = new String();

        if (lSecond <= 0) {
            szTime = "00" + ":" + "00" + ":" + "00";
        } else {
            long hour = lSecond / 3600;
            long minute = (lSecond - hour * 3600) / 60;
            long second = (lSecond - hour * 3600 - minute * 60);

            if (hour <= 0) {
                szTime = "00";
            } else if (hour < 10) {
                szTime = "0" + String.valueOf(hour);
            } else {
                szTime = String.valueOf(hour);
            }
            szTime = szTime + ":";

            if (minute <= 0) {
                szTime = szTime + "00";
            } else if (minute < 10) {
                szTime = szTime + "0" + String.valueOf(minute);
            } else {
                szTime = szTime + String.valueOf(minute);
            }
            szTime = szTime + ":";

            if (second <= 0) {
                szTime = szTime + "00";
            } else if (second < 10) {
                szTime = szTime + "0" + String.valueOf(second);
            } else {
                szTime = szTime + String.valueOf(second);
            }
        }

        return szTime;
    }

    public static String getFormattedDateUtil(Date dtDate, String strFormatTo) {
        if (dtDate == null) {
            return "";
        }
        strFormatTo = strFormatTo.replace('/', '-');
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(strFormatTo);
            return formatter.format(dtDate);
        } catch (Exception e) {
            // Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
            return "";
        }
    }

    /**
     * 得出两个日期之间的间隔天数
     *
     * @param strFromDate 格式为yyyyMMdd
     * @param strToDate   格式为yyyyMMdd
     * @return int
     */
    public static int getBetweenDays(String strFromDate, String strToDate) {
        try {
            Calendar clFrom = new GregorianCalendar();
            int iYear = Integer.parseInt(strFromDate.substring(0, 4));
            int iMonth = Integer.parseInt(strFromDate.substring(4, 6));
            int iDay = Integer.parseInt(strFromDate.substring(6, 8));
            clFrom.set(iYear, iMonth, iDay, 0, 0, 0);
            Calendar clTo = new GregorianCalendar();
            iYear = Integer.parseInt(strToDate.substring(0, 4));
            iMonth = Integer.parseInt(strToDate.substring(4, 6));
            iDay = Integer.parseInt(strToDate.substring(6, 8));
            clTo.set(iYear, iMonth, iDay, 0, 0, 0);
            long llTmp = clTo.getTime().getTime() - clFrom.getTime().getTime();
            return new Long(llTmp / 1000 / 3600 / 24).intValue();
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    // 原DateUtil方法
    private static DateUtil instance = null;

    private static final Locale local = Locale.ENGLISH;

    public static synchronized DateUtil getInstance() {
        if (instance == null) {
            instance = new DateUtil();
        }
        return instance;
    }

    public static final long millisInDay = 86400000;

    // some staticByhang date formats
    private static SimpleDateFormat[] mDateFormats = loadDateFormats();

    private static final SimpleDateFormat mFormat8chars = new SimpleDateFormat("yyyyMMdd");

    private static final SimpleDateFormat mFormatIso8601Day = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat mFormatIso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    // http://www.w3.org/Protocols/rfc822/Overview.html#z28
    private static final SimpleDateFormat mFormatRfc822 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

    private static final SimpleDateFormat mFormatTradeEasy = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private static final SimpleDateFormat mFormatTradeEasyMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");

    // add by huyanzhi
    private static final SimpleDateFormat mFormatTradeEasyProduct = new SimpleDateFormat("dd/MM/yyyy");
    // end

    private static final SimpleDateFormat mFormatExpire = new SimpleDateFormat("MMMM dd, yyyy", local);

    private static SimpleDateFormat[] loadDateFormats() {
        SimpleDateFormat[] temp = {
                // new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a"),
                new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy"),
                // standard Date.toString() results
                new SimpleDateFormat("M/d/yy hh:mm:ss"), new SimpleDateFormat("M/d/yyyy hh:mm:ss"), new SimpleDateFormat("M/d/yy hh:mm a"), new SimpleDateFormat("M/d/yyyy hh:mm a"),
                new SimpleDateFormat("M/d/yy HH:mm"), new SimpleDateFormat("M/d/yyyy HH:mm"), new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS"),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
                // standard Timestamp.toString() results
                new SimpleDateFormat("M-d-yy HH:mm"), new SimpleDateFormat("M-d-yyyy HH:mm"), new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS"), new SimpleDateFormat("M/d/yy"),
                new SimpleDateFormat("M/d/yyyy"), new SimpleDateFormat("M-d-yy"), new SimpleDateFormat("M-d-yyyy"), new SimpleDateFormat("MMMM d, yyyyy"), new SimpleDateFormat("MMM d, yyyyy")};

        return temp;
    }

    // -----------------------------------------------------------------------

    /**
     * Gets the array of SimpleDateFormats that DateUtil knows about.
     **/
    private static SimpleDateFormat[] getFormats() {
        return mDateFormats;
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a Date set to the last possible millisecond of the day, just
     * before midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getEndOfDay(Date day) {
        return getEndOfDay(day, Calendar.getInstance());
    }

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * Returns a Date set just to Noon, to the closest possible millisecond of
     * the day. If a null day is passed in, a new Date is created. nnoon (00m
     * 12h 00s)
     */
    public static Date getNoonOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * 根据传入的日期字符串转换成相应的日期对象，如果字符串为空或不符合日期格 式，则返回当前时间。
     *
     * @param strDate String 日期字符串
     * @return java.util.Date 日期对象
     */
    public static Date getDateFromString(String strDate) {


        if (strDate.length() == 10) {
            strDate += " 00:00:00";

        }

        if (StringUtils.isEmpty(strDate)) {
            return new Date(System.currentTimeMillis());
        }
        try {
            return sdfLongTimePlus.parse(strDate);
        } catch (Exception ex) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    // -----------------------------------------------------------------------
    public static Date parseFromFormats(String aValue) {
        if (CheckStringUtil.isEmpty(aValue))
            return null;

        // get DateUtil's formats
        SimpleDateFormat formats[] = DateUtil.getFormats();
        if (formats == null)
            return null;

        // iterate over the array and parse
        Date myDate = null;
        for (int i = 0; i < formats.length; i++) {
            try {
                myDate = DateUtil.parse(aValue, formats[i]);
                // if (myDate instanceof Date)
                return myDate;
            } catch (Exception e) {
                // do nothing because we want to try the next
                // format if current one fails
            }
        }
        // haven't returned so couldn't parse
        return null;
    }

    // -----------------------------------------------------------------------
    public static Timestamp parseTimestampFromFormats(String aValue) {
        if (CheckStringUtil.isEmpty(aValue))
            return null;

        // call the regular Date formatter
        Date myDate = DateUtil.parseFromFormats(aValue);
        if (myDate != null)
            return new Timestamp(myDate.getTime());
        return null;
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a java.sql.Timestamp equal to the current time
     **/
    public static Timestamp now() {
        return new Timestamp(new Date().getTime());
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a string the represents the passed-in date parsed according to
     * the passed-in format. Returns an empty string if the date or the format
     * is null.
     **/
    public static String format(Date aDate, SimpleDateFormat aFormat) {
        if (aDate == null || aFormat == null) {
            return "";
        }
        synchronized (aFormat) {
            return aFormat.format(aDate);
        }
    }

    // -----------------------------------------------------------------------

    /**
     * Tries to take the passed-in String and format it as a date string in the
     * the passed-in format.
     **/
    public static String formatDateString(String aString, SimpleDateFormat aFormat) {
        if (CheckStringUtil.isEmpty(aString) || aFormat == null)
            return "";
        try {
            Timestamp aDate = parseTimestampFromFormats(aString);
            if (aDate != null) {
                return DateUtil.format(aDate, aFormat);
            }
        } catch (Exception e) {
            // Could not parse aString.
        }
        return "";
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a Date using the passed-in string and format. Returns null if the
     * string is null or empty or if the format is null. The string must match
     * the format.
     **/
    public static Date parse(String aValue, SimpleDateFormat aFormat) throws ParseException {
        if (CheckStringUtil.isEmpty(aValue) || aFormat == null) {
            return null;
        }

        return aFormat.parse(aValue);
    }

    // -----------------------------------------------------------------------

    /**
     * Returns true if endDate is after startDate or if startDate equals endDate
     * or if they are the same date. Returns false if either value is null.
     **/
    public static boolean isValidDateRange(Date startDate, Date endDate) {
        return isValidDateRange(startDate, endDate, true);
    }

    // -----------------------------------------------------------------------

    /**
     * Returns true if endDate is after startDate or if startDate equals
     * endDate. Returns false if either value is null. If equalOK, returns true
     * if the dates are equal.
     **/
    public static boolean isValidDateRange(Date startDate, Date endDate, boolean equalOK) {
        // false if either value is null
        if (startDate == null || endDate == null) {
            return false;
        }

        if (equalOK) {
            // true if they are equal
            if (startDate.equals(endDate)) {
                return true;
            }
        }

        // true if endDate after startDate
        if (endDate.after(startDate)) {
            return true;
        }

        return false;
    }

    // -----------------------------------------------------------------------
    // returns full timestamp format
    public static SimpleDateFormat defaultTimestampFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    // -----------------------------------------------------------------------
    // convenience method returns minimal date format
    public static SimpleDateFormat get8charDateFormat() {
        return DateUtil.mFormat8chars;
    }

    // -----------------------------------------------------------------------
    // convenience method returns minimal date format
    public static SimpleDateFormat defaultDateFormat() {
        return DateUtil.friendlyDateFormat(true);
    }

    // -----------------------------------------------------------------------
    // convenience method
    public static String defaultTimestamp(Date date) {
        return DateUtil.format(date, DateUtil.defaultTimestampFormat());
    }

    // -----------------------------------------------------------------------
    // convenience method
    public static String defaultDate(Date date) {
        return DateUtil.format(date, DateUtil.defaultDateFormat());
    }

    // -----------------------------------------------------------------------
    // convenience method returns long friendly timestamp format
    public static SimpleDateFormat friendlyTimestampFormat() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    // -----------------------------------------------------------------------
    // convenience method returns long friendly formatted timestamp
    public static String friendlyTimestamp(Date date) {
        return DateUtil.format(date, DateUtil.friendlyTimestampFormat());
    }

    // -----------------------------------------------------------------------
    // convenience method returns long friendly formatted timestamp
    public static String format8chars(Date date) {
        return DateUtil.format(date, mFormat8chars);
    }

    // -----------------------------------------------------------------------
    // convenience method returns long friendly formatted timestamp
    public static String formatIso8601Day(Date date) {
        return DateUtil.format(date, mFormatIso8601Day);
    }

    public static String formatIso8601Day(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return DateUtil.format(calendar.getTime(), mFormatIso8601Day);
    }

    public static String formatTradeEasy(Date date) {
        return DateUtil.format(date, mFormatTradeEasy);
    }

    // add by huyanzhi
    public static String formatTradeEasyProduct(Date date) {
        return DateUtil.format(date, mFormatTradeEasyProduct);
    }

    //

    public static String formatFormatTradeEasyMMddyyyy(Date date) {
        return DateUtil.format(date, mFormatTradeEasyMMddyyyy);
    }

    public static String formatTradeEasy(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        return DateUtil.format(calendar.getTime(), mFormatTradeEasy);
    }

    // -----------------------------------------------------------------------
    public static String formatRfc822(Date date) {
        return DateUtil.format(date, mFormatRfc822);
    }

    public static String formatExpire(Date date) {
        return DateUtil.format(date, mFormatExpire);
    }

    // -----------------------------------------------------------------------
    // This is a hack, but it seems to work
    public static String formatIso8601(Date date) {
        if (date == null)
            return "";

        // Add a colon 2 chars before the end of the string
        // to make it a valid ISO-8601 date.

        String str = DateUtil.format(date, mFormatIso8601);
        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, str.length() - 2));
        sb.append(":");
        sb.append(str.substring(str.length() - 2));
        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // convenience method returns minimal date format
    public static SimpleDateFormat minimalDateFormat() {
        return DateUtil.friendlyDateFormat(true);
    }

    // -----------------------------------------------------------------------
    // convenience method using minimal date format
    public static String minimalDate(Date date) {
        return DateUtil.format(date, DateUtil.minimalDateFormat());
    }

    // -----------------------------------------------------------------------
    // convenience method that returns friendly data format
    // using full month, day, year digits.
    public static SimpleDateFormat fullDateFormat() {
        return DateUtil.friendlyDateFormat(false);
    }

    // -----------------------------------------------------------------------
    public static String fullDate(Date date) {
        return DateUtil.format(date, DateUtil.fullDateFormat());
    }

    // -----------------------------------------------------------------------

    /**
     * Returns a "friendly" date format.
     *
     * @param minimalFormat Should the date format allow single digits.
     **/
    public static SimpleDateFormat friendlyDateFormat(boolean minimalFormat) {
        if (minimalFormat) {
            return new SimpleDateFormat("d.M.yy");
        }

        return new SimpleDateFormat("dd.MM.yyyy");
    }

    // -----------------------------------------------------------------------

    /**
     * Format the date using the "friendly" date format.
     */
    public static String friendlyDate(Date date, boolean minimalFormat) {
        return DateUtil.format(date, DateUtil.friendlyDateFormat(minimalFormat));
    }

    // -----------------------------------------------------------------------
    // convenience method
    public static String friendlyDate(Date date) {
        return DateUtil.format(date, DateUtil.friendlyDateFormat(true));
    }

    public static Date parseFormatIso8601Date(String date) throws Exception {
        Date returnDate = null;
        try {
            returnDate = mFormatIso8601Day.parse(date);
        } catch (Exception e) {
            throw e;
        }
        return returnDate;
    }

    // add by huyanzhi
    public static String addDate(String date, String type, int into) throws Exception {
        String Sdate = "";
        try {
            GregorianCalendar grc = new GregorianCalendar();
            grc.setTime(new Date(date));
            if (type.equals("D")) {
                grc.add(GregorianCalendar.DATE, into);
            } else if (type.equals("M")) {
                grc.add(GregorianCalendar.MONTH, into);
            } else if (type.equals("Y")) {
                grc.add(GregorianCalendar.YEAR, into);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Sdate = new String(formatter.format(grc.getTime()));
        } catch (Exception e) {
            throw e;
        }
        return Sdate;
    }

    public static String addDate(String date, String into) throws Exception {
        String Sdate = "";
        try {
            date = date.replaceAll("-", "/");
            date = date.substring(0, date.length() - 2);
            GregorianCalendar grc = new GregorianCalendar();
            grc.setTime(new Date(date));
            grc.add(GregorianCalendar.DATE, Integer.parseInt(into));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Sdate = new String(formatter.format(grc.getTime()));
        } catch (Exception e) {
            throw e;
        }
        return Sdate;
    }

    public static String formatDate(Date date, String pattern) {
        if (pattern == null || pattern.equals("") || pattern.equals("null")) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    //

    public static String addValidateDate(String date, String into) throws Exception {
        String Sdate = "";
        try {
            date = date.replaceAll("-", "/");
            date = date.substring(0, date.length() - 2);
            GregorianCalendar grc = new GregorianCalendar();
            grc.setTime(new Date(date));
            grc.add(GregorianCalendar.DATE, Integer.parseInt(into));
            Sdate = new String(mFormatExpire.format(grc.getTime()));
        } catch (Exception e) {
            throw e;
        }
        return Sdate;
    }

    public static String addDayToStringDate(String formate, String strDate, String days) {
        String stringDate = null;
        try {
            Date date = fromStringToDate(formate, strDate);
            long now = date.getTime() + (long) Integer.parseInt(days) * DAY_IN_MILLISECOND;

            stringDate = getFomartDate(new Date(now), formate);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return stringDate;
    }

    public static Date addDayToStringDate2(String formate, String strDate, String days) {
        Date date = null;
        try {
            date = fromStringToDate(formate, strDate);
            long now = date.getTime() + (long) Integer.parseInt(days) * DAY_IN_MILLISECOND;

            date = new Date(now);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return date;
    }

    public static Date dateDayAdd(Date date, int days) {
        long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
        return new Date(now);
    }

    /**
     * 字符串形式转化为Date类型 String类型按照format格式转为Date类型
     **/
    public static Date fromStringToDate(String format, String dateTime) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        date = sdf.parse(dateTime);
        return date;
    }

    /**
     * 字符串形式转化为Date类型 String类型按照format格式转为Date类型
     **/
    public static Date fromStringToDate(Date date) throws ParseException {
        return sdfLongTimePlus.parse(sdfLongTimePlus.format(date));
    }

    /**
     * <br>
     * <b>功能：</b>返回时分秒:HHmmss<br>
     * <b>作者：</b>兰伟<br>
     * <b>日期：</b>2013-11-05<br>
     *
     * @param date
     * @return
     */
    public static Integer getTimeFormatIntger(Date date) {
        if (date == null) {
            return 0;
        }
        String strTemp = DateUtil.getFomartDate(date, "yyyyMMddHHmmss");
        String nowTime = strTemp.substring(8, 14);
        return Integer.valueOf(nowTime);
    }

    /**
     * @param format
     * @param format 日期格式
     * @return String
     */
    public static String toDayToStr(String format) {
        try {
            Date now = new Date();
            return DateToStr(now, format) + " " + getWeekOfDate(now);
        } catch (Exception e) {
            System.out.println("Date 转 String 类型失败: " + e);
            return null;
        }
    }

    /**
     * @param date
     * @param format 日期格式
     * @return String
     */
    public static String DateToStr(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            System.out.println("Date 转 String 类型失败: " + e);
            return null;
        }
    }

    /**
     * @param date, int days
     * @return DATE 型加具体的天数
     */
    public static Date dateAddDays(Date date, int days) {
        long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
        return new Date(now);
    }

    /**
     * @param date,String fFormatStr eg:yyyy-MM-dd HH:mm:ss
     * @return 将DATE 转换成字符性日期格式
     */
    public static String dateTypeToString(Date date, String fFormatStr) {
        // yyyy-MM-dd HH:mm:ss
        SimpleDateFormat dateformat = new SimpleDateFormat(fFormatStr);
        String strDate = dateformat.format(date);
        return strDate;
    }

    /**
     * @param fFormatStr yyyy-MM-dd
     * @获取当前的系统时间，并按照固定的格式初始话
     */
    public static String getStringOfNowDate(String fFormatStr) {
        String nowDateString = dateTypeToString(new Date(), fFormatStr);
        return nowDateString;
    }

    /**
     * @ 获取当月的第一天，2009-05-01
     */
    public static String getStringOfFirstDayInMonth() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String temp = sdf.format(date);
        String firstDayInMoth = "";
        firstDayInMoth = temp + "-01";

        return firstDayInMoth;

    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 获取当前日期是周几<br>
     *
     * @param dt
     * @return 当前日期是周几
     */
    public static String getWeekZJOfDate(Date dt) {
        String[] weekDays = {"周天", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * yyyy-MM-dd 的日期格式
     *
     * @param strDate
     * @author lanwei 2013-12-11
     */
    public static Timestamp getDateDayByString(String strDate) {
        if (strDate == null || strDate.trim().equals("")) {
            return new Timestamp(System.currentTimeMillis());
        }
        try {
            strDate = getFormattedDate(strDate, "yyyy-MM-dd") + " 00:00:00.000000000";
            return Timestamp.valueOf(strDate);
        } catch (Exception ex) {
            return new Timestamp(System.currentTimeMillis());
        }
    }

    /**
     * 获得本周的周一
     */
    public static String getMonday() {
        Calendar hh = Calendar.getInstance(Locale.CHINA);
        hh.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDateLong(hh.getTime());
    }

    /**
     * 获得本周的周天
     */
    public static String getSunday() {
        Calendar hh = Calendar.getInstance(Locale.CHINA);
        hh.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        hh.add(Calendar.WEEK_OF_YEAR, 1);
        return getDateLong(hh.getTime());
    }

    /**
     * 日期格式2012-04-05 获得日期对应的周一
     */
    public static String getMondayByStr(String dateStr) {
        Calendar hh = Calendar.getInstance(Locale.CHINA);
        hh.setTime(Timestamp.valueOf(getFormattedDate(dateStr, "yyyy-MM-dd") + " 00:00:00.000000000"));
        hh.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return getDateLong(hh.getTime());
    }

    /**
     * 日期格式2012-04-05 获得日期对应的周天
     */
    public static String getSundayByStr(String dateStr) {
        Calendar hh = Calendar.getInstance(Locale.CHINA);
        hh.setTime(Timestamp.valueOf(getFormattedDate(dateStr, "yyyy-MM-dd") + " 00:00:00.000000000"));
        hh.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        hh.add(Calendar.WEEK_OF_YEAR, 1);

        return getDateLong(hh.getTime());
    }

    public static Integer getWeekByStr(String dateStr) {
        Calendar hh = Calendar.getInstance(Locale.CHINA);
        hh.setTime(Timestamp.valueOf(getFormattedDate(dateStr, "yyyy-MM-dd") + " 00:00:00.000000000"));
        return hh.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean isValidDate(String s, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            dateFormat.setLenient(false);
            dateFormat.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }


    public static String strStartDateString(String startString) {
        return startString = startString + "-01 00:00:00";
    }


    public static String strEndDateString(String endString) throws ParseException {
        String fomart_String = endString + "-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date data = format.parse(fomart_String);
        lastDayOfMonth(data);
        return endString = endString + "-" + lastDayOfMonth(data) + " 23:59:59";
    }


    public static String strMonthEndDateString(String endString) throws Exception {
        String endStr = endString.replaceAll("-", "/");
        System.out.println("aa=" + endStr);
        endStr = endStr += "/01";
        endStr = addDate(endStr, "M", 1) + " 00:00:00";
        return endStr;
    }

    /**
     * @param startString
     * @return
     */
    public static String startDateString(String startString) {
        return startString = startString + " 00:00:00";
    }

    public static String endDateString(String endString) throws ParseException {
        return endString = endString + " 23:59:59";
    }

    /***
     * 获取当前月的最后一天
     *
     */
    public static int lastDayOfMonth(Date date) {
        Calendar cla = Calendar.getInstance();
        cla.setTime(date);
        cla.set(Calendar.DAY_OF_MONTH, 1);
        cla.roll(Calendar.DAY_OF_MONTH, -1);
        return cla.getTime().getDate();
    }

    /***
     * 根据string 转换 Timestamp
     * @throws ParseException
     *
     *
     *
     */
    public static Timestamp retTimestampString(String tiie) throws ParseException {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (tiie != null && tiie.length() > 0) {
            /*if(tiie.contains("/")){
                tiie =  tiie.replaceAll("/", "-");
			}*/
            if (tiie.trim().length() > 10) {
                date = sFormat.parse(tiie);
            } else {
                tiie = tiie.trim() + " 00:00:00";
                date = sFormat.parse(tiie);
            }
            return new Timestamp(date.getTime());
        }
        return null;
    }


    /**
     * 两个月份相差天数
     * 参数类型  ‘2014-11’
     *
     * @param
     */
    public static int getIntDayBettenTwoMonth(String oneMonth, String twoMonth) {

        int count = 0;
        String[] oneM = oneMonth.split("-");
        String[] twoM = twoMonth.split("-");

        int oneY = Integer.parseInt(oneM[0]);
        int twoY = Integer.parseInt(twoM[0]);
        int oneMS = Integer.parseInt(oneM[1]);
        int twoMS = Integer.parseInt(twoM[1]);

        //后面大于前面年份
        int c_y = (twoY - oneY) * 12;
        int c_m = (twoMS - oneMS);
        count = c_y + c_m;
        return count;
    }

    /**
     * 获取当前日期的前一天
     *
     * @param
     * @return
     */
    public static String getPreviousDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String previousDay = sdf.format(date);
        return previousDay;
    }

    /**
     * 根据传入的日期区间返回在区间之间的日期例如：2015-01~2015-03 返回的list中的数据为2015-01,2015-02,2015-03
     *
     * @param sDate  区间开始日期
     * @param eDate  区间结束日期
     * @param format 需要日期的格式 例如 yyyy-MM-dd
     * @return
     * @author chenmeng
     */
    public static List<String> getMuilMonth(String sDate, String eDate, String format) {
        JDateTime startDateIn = new JDateTime(sDate);
        JDateTime endDateIn = new JDateTime(eDate);
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (startDateIn.compareDateTo(endDateIn) == 0) {
            Date startDate = startDateIn.convertToDate();
            list.add(sdf.format(startDate));
        } else {
            while (startDateIn.compareDateTo(endDateIn) != 1) {
                Date date = startDateIn.convertToDate();
                list.add(sdf.format(date));
                startDateIn.addMonth(1);
            }
        }
        return list;
    }

    //获取年份列表
    public static List<Integer> getYearList() {
        List<Integer> yearList = new ArrayList<Integer>();
        JDateTime jd = new JDateTime();
        JDateTime jd2 = new JDateTime();
        yearList.add(jd.getYear());
        yearList.add(jd.addYear(1).getYear());
        yearList.add(jd.addYear(1).getYear());
        yearList.add(jd.addYear(1).getYear());
        yearList.add(jd2.addYear(-1).getYear());
        yearList.add(jd2.addYear(-1).getYear());
        yearList.add(jd2.addYear(-1).getYear());
        Collections.sort(yearList, new Comparator<Integer>() {
            @Override
            public int compare(Integer arg0, Integer arg1) {
                int k = 0;
                int i = Integer.parseInt(String.valueOf(arg0));
                int j = Integer.parseInt(String.valueOf(arg1));
                if (i == j) {
                    k = 0;
                } else if (i > j) {
                    k = 1;
                } else if (i < j) {
                    k = -1;
                }
                return k;
            }
        });
        return yearList;
    }


    /**
     * 计费期开始时间   结束时间验证
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> returnFeeDateBettenNumber(String startDate, String endDate) {
        List<String> fee_data = new ArrayList<String>();
        String[] start = startDate.split("-");
        String[] end = endDate.split("-");
        String s_y = start[0];
        String s_m = start[1];
        String e_y = end[0];
        String e_m = end[1];
        //验证年份  开始年份大于结束年份  输入错误
        if (Integer.parseInt(s_y) > Integer.parseInt(e_y)) {
            return null;
        }
        //验证年份 相同年份下 开始月份大于结束月份
        if (Integer.parseInt(s_y) == Integer.parseInt(e_y)) {
            if (Integer.parseInt(s_m) > Integer.parseInt(e_m)) {
                return null;
            } else if (Integer.parseInt(s_m) == Integer.parseInt(e_m)) {
                //月份相同
                fee_data.add(s_y + "-" + s_m);
            } else {

                int st_m = Integer.parseInt(s_m);
                int s = Integer.parseInt(e_m) - Integer.parseInt(s_m);
                for (int i = 0; i <= s; i++) {
                    if (i == 0) {
                        fee_data.add(s_y + "-" + s_m);
                    } else {
                        st_m = st_m + 1;
                        if (st_m < 10) {
                            fee_data.add(s_y + "-0" + st_m);
                        } else {
                            fee_data.add(s_y + "-" + st_m);
                        }
                    }
                }
            }
        }
        //验证年份  开始年份小于结束年份
        if (Integer.parseInt(s_y) < Integer.parseInt(e_y)) {
            int y = Integer.parseInt(e_y) - Integer.parseInt(s_y);
            int m_number = y * 12;
            if (Integer.parseInt(s_m) > Integer.parseInt(e_m)) {
                int s = Integer.parseInt(s_m) - Integer.parseInt(e_m);
                m_number = m_number - s;
            } else if (Integer.parseInt(s_m) < Integer.parseInt(e_m)) {
                int s = Integer.parseInt(e_m) - Integer.parseInt(s_m);
                m_number = m_number + s;
            }
            int st_y = Integer.parseInt(s_y);
            int st_m = Integer.parseInt(s_m);
            for (int i = 0; i <= m_number; i++) {
                if (i == 0) {
                    fee_data.add(s_y + "-" + s_m);
                } else {
                    st_m = st_m + 1;
                    if (st_m > 12) {
                        st_y = st_y + 1;
                        st_m = 1;
                    }
                    if (st_m < 10) {
                        fee_data.add(st_y + "-0" + st_m);
                    } else {
                        fee_data.add(st_y + "-" + st_m);
                    }
                }
            }
        }
        return fee_data;
    }


    /**
     * 获取上月 月份 说
     */
    public static String getLastMonth(String date) {
        String[] data = date.split("-");
        Integer y = Integer.parseInt(data[0]);
        Integer m = Integer.parseInt(data[1]);
        String r_y = "";
        String r_m = "";
        if (m - 1 == 0) {
            r_m = "12";
            y = y - 1;
            if (y < 1900) {
                r_m = "1";
                r_y = "1900";
            } else {
                r_y = String.valueOf(y);
            }
        } else {
            r_m = String.valueOf(m - 1);
            r_y = String.valueOf(y);
        }

        if (Integer.parseInt(r_m) < 10) {
            r_m = "0" + r_m;
        }
        return r_y + "-" + r_m;
    }

    public static void main(String[] args) {
        System.out.println("lastMonth:" + getLastMonth("1900-01"));
        String d = "19900810";
        DateFormat f = new SimpleDateFormat("yyyyMMdd");

        try {
            System.out.println(getAgeByBirthday(f.parse(d)));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException Date 返回类型
     * @throws
     * @Title: longToDate
     * @Description: 日期 long 转 date
     * @author h
     * @date Nov 23, 2016 3:19:52 PM
     * @version V1.0
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException String 返回类型
     * @throws
     * @Title: longToString
     * @Description: 日期  long 转 String
     * @author h
     * @date Nov 23, 2016 3:20:34 PM
     * @version V1.0
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * 获取指定和日期的前一天
     *
     * @param
     * @return
     */
    public static String getSpecifiedDayBefore() {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String specifiedDay = getNowTimestamp();
//        Calendar c = Calendar.getInstance();
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        c.setTime(date);
//        int day = c.get(Calendar.DATE);
//        c.set(Calendar.DATE, day - 1);
//
//        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
//        return dayBefore;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String previousDay = sdf.format(date);
        return previousDay;
    }
}