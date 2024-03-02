package brew.brew_common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static  boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String remove(String str, char remove) {
        if(isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }

    public static String validChkDate(String strDate) {
        String _strDate = strDate;

        if(strDate == null || !(strDate.trim().length() == 8 || strDate.trim().length() == 10)) {
            throw new IllegalArgumentException("Invalid date format: " + strDate);
        }
        if(strDate.length() == 10) {
            _strDate = removeMinusChar(strDate);
        }
        return _strDate;
    }

    /**
     * 날짜 증감 계산
     * @param date
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String addYearMonthDay(String date, int year, int month, int day) {
        String strDate = validChkDate(date);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        try{
            cal.setTime(sdf.parse(strDate));
        }catch (ParseException e) {
            throw new IllegalArgumentException("Invalid Date Format : " + strDate);
        }
        if(year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if(month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if(day != 0) {
            cal.add(Calendar.DATE, day);
        }
        return sdf.format(cal.getTime());
    }

    /**
     * 연도만 증감
     * @param strDate
     * @param year
     * @return
     */
    public static String addYear(String strDate, int year) {
        return addYearMonthDay(strDate, year, 0, 0);
    }

    /**
     * 월만 증감
     * @param strDate
     * @param month
     * @return
     */
    public static String addMonth(String strDate, int month) {
        return addYearMonthDay(strDate, 0, month, 0);
    }

    /**
     * 일만 증감
     * @param strDate
     * @param day
     * @return
     */
    public static String addDay(String strDate, int day) {
        return addYearMonthDay(strDate, 0, 0, day);
    }

    public static boolean checkDate(String strDate) {
        String date = validChkDate(strDate);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6);
        return checkDate(year, month, day);
    }

    public static boolean checkDate(String year, String month, String day) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
            Date result = formatter.parse(year + "." + month + "." + day);
            String resultStr = formatter.format(result);
            if (resultStr.equalsIgnoreCase(year + "." + month + "." + day)) {
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 윤달 계산
     * @param year
     * @return
     */
    public String leapYear(int year) {
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return "29";
        }
        return "28";
    }

    /**
     * 윤년 여부
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return false;
        }
        return true;
    }

    /**
     * 오늘날짜 가져오는 함수 -시간까지(YYYY-MM-DD HH24:MI:ss포맷)
     * @return yyyy-MM-dd HH:mm:ss 형태의 오늘 날짜
     * ex> '2020-11-17 23:59:59'
     */
    public static String getTodayTime() {
        SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentTime = new Date ();
        String today = sdf.format(currentTime);

        return today;
    }

    /**
     * 전월계산
     * @param strDate('yyyyMM')
     * @return
     */
    public static String getLastMonth(String strDate){

        String lastMonth = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM", Locale.KOREAN);
            Calendar cal = Calendar.getInstance();
            Date sMonth = sdf.parse(strDate);

            cal.setTime(sMonth);
            cal.add(Calendar.MONTH, -1);

            lastMonth = sdf.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lastMonth;
    }

    public static String getLastYear(String strDate, String format){
        String lastYear = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat (format, Locale.getDefault());
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(strDate));
            cal.add(Calendar.YEAR, -1);

            lastYear = sdf.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lastYear;
    }

    public static String getDateByDiffDate(String startDate, int endDate){
        String result = "";
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(startDate);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, endDate);
            date = calendar.getTime();
            result = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }



}
