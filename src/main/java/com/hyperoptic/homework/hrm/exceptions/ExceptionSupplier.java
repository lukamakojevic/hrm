package com.hyperoptic.homework.hrm.exceptions;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class ExceptionSupplier {

    private ExceptionSupplier() {
        throw new IllegalStateException("Utility class");
    }

    public static final Supplier<HrmException> BAD_REQUEST =
            () -> HrmException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .errorCode(ErrorCodes.BAD_REQUEST)
                    .build();
}
