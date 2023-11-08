package com.hyperoptic.homework.hrm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HrmExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {HrmException.class})
  public ResponseEntity<Object> handleHrmException(HrmException hrmException) {
    return ResponseEntity.status(hrmException.getStatus()).body(hrmException.getErrorCode());
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleGeneralException(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorCodes.INTERNAL_SERVER_ERROR);
  }
}
