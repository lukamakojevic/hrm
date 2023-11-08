package com.hyperoptic.homework.hrm.exceptions;

public class ErrorCodes {

  private ErrorCodes() {
    throw new IllegalStateException("Utility class");
  }

  public static final String INTERNAL_SERVER_ERROR =
      "com.hyperoptic.homework.hrm.internalServerError";
  public static final String EMPLOYEE_NOT_FOUND = "com.hyperoptic.homework.hrm.employee.notFound";
  public static final String TEAM_NOT_FOUND = "com.hyperoptic.homework.hrm.team.notFound";
  public static final String EMPLOYEE_TEAM_LEAD_ALREADY =
      "com.hyperoptic.homework.hrm.employee.teamLeadAlready";
}
