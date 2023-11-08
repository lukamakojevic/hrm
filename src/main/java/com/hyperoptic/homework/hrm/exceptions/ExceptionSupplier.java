package com.hyperoptic.homework.hrm.exceptions;

import java.util.function.Supplier;
import org.springframework.http.HttpStatus;

public class ExceptionSupplier {

  private ExceptionSupplier() {
    throw new IllegalStateException("Utility class");
  }

  public static final Supplier<HrmException> EMPLOYEE_NOT_FOUND =
      () ->
          HrmException.builder()
              .status(HttpStatus.BAD_REQUEST)
              .errorCode(ErrorCodes.EMPLOYEE_NOT_FOUND)
              .build();

  public static final Supplier<HrmException> TEAM_NOT_FOUND =
      () ->
          HrmException.builder()
              .status(HttpStatus.BAD_REQUEST)
              .errorCode(ErrorCodes.TEAM_NOT_FOUND)
              .build();

  public static final Supplier<HrmException> EMPLOYEE_TEAM_LEAD_ALREADY =
      () ->
          HrmException.builder()
              .status(HttpStatus.BAD_REQUEST)
              .errorCode(ErrorCodes.EMPLOYEE_TEAM_LEAD_ALREADY)
              .build();
}
