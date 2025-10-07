package com.searchonmath.arxivdownloader.util;

import feign.FeignException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FeignUtil {

    public static void feignExceptionHandler(FeignException e){
        log.error("Error when get page with feign client");
        log.error("Status: {}", e.status());
        log.error("Request: {}", e.request());
        log.error("Response: {}", e.responseBody());
        log.error("Exception Message: {}", e.getMessage());
    }

}
