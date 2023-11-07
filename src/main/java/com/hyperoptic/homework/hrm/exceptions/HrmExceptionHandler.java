package com.hyperoptic.homework.hrm.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HrmExceptionHandler {

    @ExceptionHandler(value = {HrmException.class})
    public ResponseEntity<Object> handleHrmExceptions(HrmException hrmException) {
        return ResponseEntity.status(hrmException.getStatus())
                .body(hrmException.getErrorCode());
    }
}
