package com.wxy.wjl.testspringboot2.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 年月日格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_FORMAT2 = "yyyy年MM月dd日";
    /**
     * 时分秒格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HHmmss";
    public static final String HOUR_MIN_SEC_FORMAT = "HH:mm:ss";
    public static final String HOUR_MIN_SEC_FORMAT2 = "HH时mm分ss秒";
    /**
     * 时间戳格式
     */
    public static final String DEFAULT_TM_SMP_FORMAT = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_FORMAT_SEC2 = "yyyy年MM月dd日 HH时mm分ss秒";

    private static final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 获取当前日期 yyyyMMdd 8位字符串
     * @return
     */
    public static String getDateStr() {
        return dateToString(new Date(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 获取当前时间戳 yyyyMMddHHmmss 14位字符串
     * @return
     */
    public static String getTmSmp() {
        return dateToString(new Date(), DEFAULT_DATE_FORMAT);
    }

    /**
     * 根据格式获取日期字符串
     * @return
     */
    public static String getDateStrByFormat(String format) {
        return dateToString(new Date(), format);
    }


    /**
     * 对Date格式化 返回Date
     * @param date
     * @param formatStr
     * @return
     */
    public static Date formatDate(Date date, String formatStr) {
        Date ret = null;
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
        String dateStr = formatter.format(date);
        try {
            ret = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 对Date格式化 返回String
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        String ret = "";
        if (date == null) {
            return ret;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ret = formatter.format(date);
        return ret;
    }

    /**
     * str日期类型转DATE
     */
    public static Date strToDate(String dateStr, String format) {
        Date ret = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            ret = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 日期增加 分钟
     * @param date
     * @param min
     * @return
     */
    public static Date addMin(Date date, int min) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, min);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 日期增加 小时
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        Date newDate = c.getTime();
        return newDate;
    }
    /**
     * Date增加 天
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 日期增加 月
     * @param date
     * @param month
     * @return
     */
    public static Date addMonths(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 日期增加 年
     * @param date
     * @param year
     * @return
     */
    public static Date addYears(Date date, int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, year);
        Date newDate = c.getTime();
        return newDate;
    }

    /**
     * 增加时间
     * @param date 时间
     * @param calendarField 时间格式 Calendar类的常量 例如Calendar.YEAR表示年
     * @param amount 间隔
     * @return 日期
     */
    public static Date addDate(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date could not be null!");
        }
        if (amount == 0) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, amount);
        return calendar.getTime();
    }



    /**
     * 日期计算 返回相差天数
     * @param before
     * @param after
     * @return
     */
    public static int getDistanceDays(Date before, Date after) {
        long diff = after.getTime() - before.getTime();
        return Long.valueOf(diff / (1000 * 60 * 60 * 24)).intValue();
    }

    /**
     * 获取当前小时 比如12:30 返回12
     * @return
     */
    public static int getNowHourOfDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 计算Date是本月中的第几天
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月的天数(或者说是当前月份的最后一天的日期)
     * @param now
     * @return
     */
    public static int getDaysOfMonth(Date now) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        return calender.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 毫秒数转换为时间字符串
     * @return
     */
    public static String longTime2DateStrByFormat(Long longTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String time = sdf.format(new Date(longTime));
        return time;
    }


    /**
     * 由时间戳得到毫秒数（自1970年以来经过的毫秒数）
     */
    public static long fromDateStringToLong(String tmSmp) throws ParseException{
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            date = inputFormat.parse(tmSmp);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }
        return date.getTime();
    }

    /**
     * 获取时区时间
     * @return
     */
    public static String getLocalZonedDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(new Date());
        // 获取本地时区
        OffsetDateTime odt = OffsetDateTime.now();
        ZoneOffset zoneOffset = odt.getOffset();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneOffset);
        LocalDateTime localDateTime = LocalDateTime.parse(str, dateTimeFormatter);
        ZonedDateTime utcZonedDateTime = ZonedDateTime.of(localDateTime, zoneOffset);
        ZoneOffset localZoneOffset = OffsetDateTime.now().getOffset();
        ZonedDateTime localZonedDateTime = utcZonedDateTime.withZoneSameInstant(localZoneOffset);
        return localZonedDateTime.toString();
    }

    public static void main(String[] args) throws Exception{
        //System.out.println(longTime2DateStrByFormat(fromDateStringToLong("20201012123801"),DEFAULT_DATETIME_FORMAT_SEC2));
        //System.out.println(getNowHourOfDay());
      //  System.out.println(getDistanceDays(new Date(),strToDate("20201011143000",DEFAULT_TM_SMP_FORMAT)));
        //System.out.println(longTime2DateStrByFormat(1605062124890L,"yyyyMMddHHmmss"));
        System.out.println(getLocalZonedDateTime());
    }

    private static int[] ULEAD_MONTH_DAYS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] LEAD_MONTH_DAYS = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * @param str  yyyyMMdd
     * @param op   + or -
     * @param obj  year:y month:m date:d
     * @param iNum
     * @return
     * @throws Exception
     */
    public static String calcDate(String str, String op, String obj, int iNum) throws Exception {
        int[] buf3 = new int[]{12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        if (!StringUtils.isEmpty(str) && str.length() == 8 && StringUtils.isNumeric(str)) {
            try {
                String[] pattern = new String[]{"yyyyMMdd"};
                Date dt = org.apache.commons.lang3.time.DateUtils.parseDate(str, pattern);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dt);
                int iYear = calendar.get(1);
                int iMonth = calendar.get(2) + 1;
                int iDay = calendar.get(5);
                if (obj.equals("y")) {
                    if (op.equals("+")) {
                        iYear += iNum;
                    } else if (op.equals("-")) {
                        iYear -= iNum;
                    }

                    if (iMonth == 2 && is_month_end(str)) {
                        if (is_leap_year(iYear) == 0) {
                            iDay = 29;
                        } else {
                            iDay = 28;
                        }
                    }
                } else {
                    if (obj.equals("m")) {
                        if (op.equals("+")) {
                            iMonth += iNum;
                        } else if (op.equals("-")) {
                            iMonth -= iNum;
                        }

                        calendar.set(iYear, iMonth - 1, iDay);
                        iYear = calendar.get(1);
                        if (iYear < 1900) {
                            throw new Exception("不支持1900年之前的时间计算");
                        }

                        int index;
                        if (iMonth <= 0) {
                            index = buf3[Math.abs(iMonth) % 12];
                        } else if (iMonth % 12 == 0) {
                            index = 12;
                        } else {
                            index = iMonth % 12;
                        }

                        if (is_leap_year(iYear) == 0) {
                            if (iDay > LEAD_MONTH_DAYS[index - 1]) {
                                iDay = LEAD_MONTH_DAYS[index - 1];
                            }
                        } else if (iDay > ULEAD_MONTH_DAYS[index - 1]) {
                            iDay = ULEAD_MONTH_DAYS[index - 1];
                        }
                        calendar.set(iYear, index - 1, iDay);
                        //System.out.println(calendar.getTime());
                        return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
                    }

                    if (obj.equals("d")) {
                        if (op.equals("+")) {
                            iDay += iNum;
                        } else if (op.equals("-")) {
                            iDay -= iNum;
                        }
                    }
                }

                if (iYear < 1900) {
                    throw new Exception("不支持1900年之前的时间计算");
                } else {
                    calendar.set(iYear, iMonth - 1, iDay);
                    return DateFormatUtils.format(calendar.getTime(), "yyyyMMdd");
                }
            } catch (ParseException var12) {
                throw new Exception("字符串日期转换错误，请输入合法的日期字符串：" + var12);
            }
        } else {
            throw new Exception("请输入8位日期字符串");
        }
    }

    public static boolean is_month_end(String str) throws Exception {
        if (check_date(str) == 0) {
            try {
                String[] pt = new String[]{"yyyyMMdd"};
                Date dt = org.apache.commons.lang3.time.DateUtils.parseDate(str, pt);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dt);
                int iYear = calendar.get(1);
                int iMonth = calendar.get(2) + 1;
                int iDay = calendar.get(5);
                if (is_leap_year(iYear) == 0) {
                    return LEAD_MONTH_DAYS[iMonth - 1] == iDay;
                } else {
                    return ULEAD_MONTH_DAYS[iMonth - 1] == iDay;
                }
            } catch (ParseException var7) {
                throw new Exception("字符串日期转换错误，请输入合法的日期字符串：" + var7);
            }
        }else {
            return false;
        }
    }

    //判断年份是否为润年  0：润年
    public static int is_leap_year(int iYear) throws Exception {
        //普通闰年:能被4整除但不能被100整除的年份为普通闰年。
        //世纪闰年:能被400整除的为世纪闰年。
        if (iYear < 1900) {
            return -2;
        } else if (iYear % 4 == 0) {
            if (iYear % 100 == 0) {
                return iYear % 400 == 0 ? 0 : -1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public static int check_date(String str) throws Exception {
        int iYear = Integer.parseInt(str.substring(0, 4));
        int iMonth = Integer.parseInt(str.substring(4, 6));
        int iDay = Integer.parseInt(str.substring(6, 8));
        if (iYear <= 1900) {
            return -1;
        } else if (iMonth >= 1 && iMonth <= 12) {
            if (is_leap_year(iYear) == 0) {
                return iDay > 0 && iDay <= LEAD_MONTH_DAYS[iMonth - 1] ? 0 : -3;
            } else {
                return iDay > 0 && iDay <= ULEAD_MONTH_DAYS[iMonth - 1] ? 0 : -3;
            }
        } else {
            return -2;
        }

    }
}
