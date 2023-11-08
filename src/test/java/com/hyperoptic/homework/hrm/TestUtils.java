package com.hyperoptic.homework.hrm;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.models.Team;

public class TestUtils {

  public static final Integer EMPLOYEE_ID = 123;
  public static final Integer TEAM_ID = 456;

  public static final String EMPLOYEE_NAME = "Employee name";
  public static final String TEAM_NAME = "Team name";

  public static Employee employee() {
    return Employee.builder().id(EMPLOYEE_ID).name(EMPLOYEE_NAME).teamId(TEAM_ID).build();
  }

  public static EmployeeEntity employeeEntity() {
    return employeeEntity(EMPLOYEE_NAME, null);
  }

  public static EmployeeEntity employeeEntity(String name) {
    EmployeeEntity employeeEntity = employeeEntity(name, null);
    employeeEntity.setId(null);
    return employeeEntity;
  }

  public static EmployeeEntity employeeEntity(TeamEntity team) {
    return employeeEntity(EMPLOYEE_NAME, team);
  }

  public static EmployeeEntity employeeEntity(String name, TeamEntity team) {
    return EmployeeEntity.builder().id(EMPLOYEE_ID).name(name).team(team).build();
  }

  public static Team team() {
    return Team.builder().id(TEAM_ID).name(TEAM_NAME).teamLeadId(EMPLOYEE_ID).build();
  }

  public static TeamEntity teamEntity() {
    return teamEntity(TEAM_NAME, null);
  }

  public static TeamEntity teamEntity(String name) {
    TeamEntity teamEntity = teamEntity(name, null);
    teamEntity.setId(null);
    return teamEntity;
  }

  public static TeamEntity teamEntity(EmployeeEntity teamLead) {
    return teamEntity(TEAM_NAME, teamLead);
  }

  public static TeamEntity teamEntity(String name, EmployeeEntity teamLead) {
    return TeamEntity.builder().id(TEAM_ID).name(name).teamLead(teamLead).build();
  }
}
