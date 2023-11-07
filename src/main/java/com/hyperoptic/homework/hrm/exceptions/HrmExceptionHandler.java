package com.hyperoptic.homework.hrm.exceptions;

import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleHrmExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorCodes.INTERNAL_SERVER_ERROR);
    }
}
