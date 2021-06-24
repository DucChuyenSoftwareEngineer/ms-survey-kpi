package vn.vccb.mssurveykpi.util;

import vn.vccb.mssurveykpi.common.CommonConstant;

import java.util.Collection;
import java.util.Date;

public class CheckUtil {

    public static boolean isNullOrEmpty(Object value) {
        return value == null || (value instanceof String && "".equals(value.toString().trim()));
    }

    public static boolean isNullOrEmptyCollection(Collection collection) {
        return isNullOrEmpty(collection) || collection.isEmpty();
    }

    public static boolean isDate(String value) {
        Date check;

        try {
            check = DateUtil.parse(value, CommonConstant.DATE_FORMAT);
        } catch (Exception e) {
            return false;
        }

        return !isNullOrEmpty(check);
    }

    public static boolean isNumber(String value) {
        long check;

        try {
            check = Long.parseLong(value);
        } catch (Exception e) {
            return false;
        }

        return !isNullOrEmpty(check);
    }

    public static boolean isInDateRange(Date startDate, Date endDate, Date compareDate) {
        return !(compareDate.before(startDate) || compareDate.after(endDate));
    }
}