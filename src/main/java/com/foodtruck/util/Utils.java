package com.foodtruck.util;
import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static void validateNotNull(String param, String paramValue) throws Exception {
        if(StringUtils.isBlank(paramValue)) {
            throw new Exception("Invalid Parameter - paramName: " + param + " paramValue: " + paramValue);
        }
    }
}
