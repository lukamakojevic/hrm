package com.hyperoptic.homework.hrm.repositories;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchRepositoryTest {

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private TeamRepository teamRepository;

  @Autowired private SearchRepository searchRepository;

  private static HashMap<String, TeamEntity> teamByName;
  private static HashMap<String, EmployeeEntity> employeeByName;

  private static boolean isInitialized = false;

  @BeforeEach
  void setUp() {
    if (!isInitialized) {
      setUpData();
      isInitialized = true;
    }
  }

  @ParameterizedTest
  @MethodSource("findEmployeesTestCases")
  void findEmployees(EmployeeSearchParams searchParams, List<EmployeeEntity> expectedEntities) {
    List<EmployeeEntity> result = searchRepository.findEmployees(searchParams);

    assertEquals(getEmployeeNames(expectedEntities), getEmployeeNames(result));
  }

  static Stream<Arguments> findEmployeesTestCases() {
    return Stream.of(
        arguments(
            employeeParams(null, null, null),
            List.of(gandalf, aragorn, legolas, gimli, frodo, sam, merry, pippin, boromir)),
        arguments(
            employeeParams(List.of("o"), null, null), List.of(aragorn, legolas, frodo, boromir)),
        arguments(
            employeeParams(null, List.of("Ga", "ta"), null), List.of(frodo, sam, merry, pippin)),
        arguments(employeeParams(null, null, List.of("lp", "et")), List.of(gandalf, frodo)),
        arguments(employeeParams(List.of("o"), List.of("Be"), null), List.of(frodo)),
        arguments(employeeParams(null, List.of("l", "ta"), List.of("pha")), List.of(gandalf)),
        arguments(employeeParams(List.of("dal"), null, List.of("pha")), List.of(gandalf)),
        arguments(
            employeeParams(List.of("alf"), List.of("l", "ta"), List.of("pha")), List.of(gandalf)));
  }

  @ParameterizedTest
  @MethodSource("findTeamsTestCases")
  void findTeams(TeamSearchParams searchParams, List<TeamEntity> expectedEntities) {
    List<TeamEntity> result = searchRepository.findTeams(searchParams);

    assertEquals(getTeamNames(expectedEntities), getTeamNames(result));
  }

  static Stream<Arguments> findTeamsTestCases() {
    return Stream.of(
        arguments(teamParams(null, null, null), List.of(alpha, beta, gamma, delta)),
        arguments(teamParams(List.of("l"), null, null), List.of(alpha, delta)),
        arguments(teamParams(List.of("l", "mm"), null, null), List.of(alpha, gamma, delta)),
        arguments(teamParams(null, List.of("o"), null), List.of(alpha, beta)),
        arguments(teamParams(null, null, List.of("f", "F")), List.of(alpha, beta)),
        arguments(teamParams(List.of("t"), List.of("o"), null), List.of(beta)),
        arguments(teamParams(null, List.of("Sa", "ip"), List.of("do")), List.of(beta)),
        arguments(teamParams(List.of("pha"), null, List.of("al")), List.of(alpha)),
        arguments(teamParams(List.of("pha"), List.of("las"), List.of("al")), List.of(alpha)));
  }

  private void setUpData() {
    List<TeamEntity> teams = Arrays.asList(alpha, beta, gamma, delta);
    List<EmployeeEntity> employees =
        Arrays.asList(gandalf, aragorn, legolas, gimli, frodo, sam, merry, pippin, boromir);

    teamRepository.saveAll(teams);
    employeeRepository.saveAll(employees);

    alpha.setTeamLead(gandalf);
    gandalf.setTeam(alpha);
    aragorn.setTeam(alpha);
    gimli.setTeam(alpha);
    legolas.setTeam(alpha);

    beta.setTeamLead(frodo);
    frodo.setTeam(beta);
    sam.setTeam(beta);

    merry.setTeam(gamma);
    pippin.setTeam(gamma);

    teamRepository.saveAll(teams);
    employeeRepository.saveAll(employees);
  }

  private Set<String> getEmployeeNames(List<EmployeeEntity> employeeEntities) {
    return employeeEntities.stream().map(EmployeeEntity::getName).collect(Collectors.toSet());
  }

  private Set<String> getTeamNames(List<TeamEntity> teamEntities) {
    return teamEntities.stream().map(TeamEntity::getName).collect(Collectors.toSet());
  }

  private static EmployeeSearchParams employeeParams(
      List<String> names, List<String> teamNames, List<String> leadingTeamNames) {
    return EmployeeSearchParams.builder()
        .names(names)
        .teamNames(teamNames)
        .leadingTeamNames(leadingTeamNames)
        .build();
  }

  private static TeamSearchParams teamParams(
      List<String> names, List<String> teamMemberNames, List<String> teamLeadNames) {
    return TeamSearchParams.builder()
        .names(names)
        .teamMemberNames(teamMemberNames)
        .teamLeadNames(teamLeadNames)
        .build();
  }
}
