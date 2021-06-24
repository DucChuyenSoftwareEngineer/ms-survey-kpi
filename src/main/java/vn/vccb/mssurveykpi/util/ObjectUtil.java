package vn.vccb.mssurveykpi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectUtil {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String serializable(Object object) {
        String result;

        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            result = null;
        }

        return result;
    }

    public static <T> T unserializable(String value, TypeReference<T> valueTypeRef) {
        T result;

        try {
            result = mapper.readValue(value, valueTypeRef);
        } catch (IOException e) {
            result = null;
        }

        return result;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
