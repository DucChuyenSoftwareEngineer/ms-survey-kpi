package vn.vccb.mssurveykpi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final Calendar calendar;
    private static final SimpleDateFormat dateFormat;

    static {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat();
    }

    public static Date getTime(Date date, Integer hour, Integer minute, Integer second) {
        calendar.setTime(date);

        if (!CheckUtil.isNullOrEmpty(hour)) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (!CheckUtil.isNullOrEmpty(minute)) {
            calendar.set(Calendar.MINUTE, minute);
        }
        if (!CheckUtil.isNullOrEmpty(second)) {
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        return calendar.getTime();
    }

    public static Date getSystemTime() {
        Calendar c;

        c = Calendar.getInstance();

        return getTime(c.getTime(), null, null, null);
    }

    public static Date getSystemDate() {
        return getTime(DateUtil.getSystemTime(), 0, 0, 0);
    }

    public static Date addDate(Date date, int day) {
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);

        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);

        return calendar.getTime();
    }

    public static String format(Date date, String format) {
        dateFormat.applyPattern(format);

        return dateFormat.format(date);
    }

    public static Date parse(String date, String format) {
        Date result;

        result = null;

        try {
            result = new SimpleDateFormat(format).parse(date);
        } catch (ParseException ignored) {
        }

        return result;
    }
}