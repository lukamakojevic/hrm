package com.hyperoptic.homework.hrm.exceptions;

public class ErrorCodes {

    private ErrorCodes() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INTERNAL_SERVER_ERROR = "com.hyperoptic.homework.hrm.internalServerError";
    public static final String BAD_REQUEST = "com.hyperoptic.homework.hrm.badRequest";
}
