package com.hyperoptic.homework.hrm.exceptions;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class ExceptionSupplier {

    private ExceptionSupplier() {
        throw new IllegalStateException("Utility class");
    }

    public static final Supplier<HrmException> INTERNAL_SERVER_ERROR =
            () -> HrmException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(ErrorCodes.INTERNAL_SERVER_ERROR)
                    .build();

    public static final Supplier<HrmException> NOT_FOUND =
            () -> HrmException.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .errorCode(ErrorCodes.NOT_FOUND)
                    .build();
}
