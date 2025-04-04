package com.nttdata.indhub.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionConstantsUtils {

    public static final String ERROR = "Error";

    // BAD REQUEST
    public static final String BAD_REQUEST_GENERIC = "400";
    public static final String BAD_REQUEST_CUSTOM_1 = "400-01";

    // NOT FOUND
    public static final String NOT_FOUND_GENERIC = "404";
    public static final String NOT_FOUND_CUSTOM_1 = "404-01";
    public static final String NOT_FOUND_CUSTOM_2 = "404-02";

    // INTERNAL SERVER ERROR
    public static final String INTERNAL_SERVER_GENERIC = "500";
    public static final int BAD_REQUEST_INT = 400;
    public static final int INTERNAL_SERVER_GENERIC_INT = 500;

}
