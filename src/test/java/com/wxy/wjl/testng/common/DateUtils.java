package com.wxy.wjl.testng.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
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
