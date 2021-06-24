package vn.vccb.mssurveykpi.util;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {

    private static MessageSourceAccessor accessor;

    public static void setMessageSource(MessageSource messageSource) {
        accessor = new MessageSourceAccessor(messageSource);
    }

    public static String getMessage(String code) {
        return accessor.getMessage(code);
    }

    public static String getMessage(String code, Object... params) {
        return accessor.getMessage(code, params);
    }

    public static String getMessageAndParam(String code, String... params) {
        String result;

        result = getMessage(code);
        for (int i = 0; i < params.length; i++) {
            result = result.replace(String.format("{%d}", i), getMessage(params[i]));
        }

        return result;
    }
}
