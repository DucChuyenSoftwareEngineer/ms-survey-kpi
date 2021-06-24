package vn.vccb.mssurveykpi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vn.vccb.mssurveykpi.common.CommonConstant;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class RestUtil {

    private static final Logger logger;
    private static final RestTemplate restTemplate;

    static {
        restTemplate = new RestTemplate();
        logger = LoggerFactory.getLogger(RestUtil.class);
    }

    public static String get(String url, Map<String, String > headerData) {
        return execute(url, HttpMethod.GET, null, headerData);
    }

    public static String post(String url,Object data, Map<String, String > headerData){
        return execute(url, HttpMethod.POST, data, headerData);
    };

    private static String execute(String url, HttpMethod method, Object data, Map<String, String > headerData) {
        Date startTime;
        Date endTime;
        String result;
        StringBuilder log;
        HttpHeaders headers;
        HttpEntity<?> request;
        ResponseEntity<String> response;

        result = null;
        response = null;
        log = new StringBuilder();
        startTime = DateUtil.getSystemTime();

        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> entry : headerData.entrySet()) {
            headers.set(entry.getKey(), entry.getValue());
        }

        request = new HttpEntity<>(data, headers);

        try {
            response = restTemplate.exchange(url, method, request, String.class);
            result = "Success";

            return response.getBody();
        } catch (Exception e) {
            result = "Fail";

            throw e;
        } finally {
            endTime = DateUtil.getSystemTime();

            log.append("\n---------------------------Call api---------------------------\n");
            log.append(String.format("Url: %s\n", url));
            log.append(String.format("Method: %s\n", method.name()));
            if (!CheckUtil.isNullOrEmpty(data)) {
                log.append(String.format("Request: %s = %s\n", data.getClass().getSimpleName(), ObjectUtil.serializable(data)));
            }
            log.append(String.format("Start time: %s\n", DateUtil.format(startTime, CommonConstant.DATE_TIME_FORMAT)));
            log.append(String.format("End time: %s\n", DateUtil.format(endTime, CommonConstant.DATE_TIME_FORMAT)));
            log.append(String.format("Process time: %s ms\n", endTime.getTime() - startTime.getTime()));
            log.append(String.format("Result: %s\n", result));
            if (!CheckUtil.isNullOrEmpty(response)) {
                log.append(String.format("Response: %s\n", response.getBody()));
            }
            log.append("--------------------------------------------------------------");

            logger.info(log.toString());
        }
    }
}
