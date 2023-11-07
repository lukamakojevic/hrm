package com.hyperoptic.homework.hrm.exceptions;

public class ErrorCodes {

    private ErrorCodes() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INTERNAL_SERVER_ERROR = "com.hyperoptic.homework.hrm.internalServerError";
    public static final String NOT_FOUND = "com.hyperoptic.homework.hrm.notFound";
}
