package com.hyperoptic.homework.hrm.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class HrmException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
}
