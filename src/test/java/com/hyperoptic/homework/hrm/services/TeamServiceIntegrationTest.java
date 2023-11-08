package com.hyperoptic.homework.hrm.services;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.exceptions.HrmException;
import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamServiceIntegrationTest {

  @Autowired private TeamRepository teamRepository;

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private TeamService teamService;

  @BeforeEach
  void setUp() {
    teamRepository.deleteAll();
    employeeRepository.deleteAll();
  }

  @Test
  void create() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());

    Team team = team();
    team.setTeamLeadId(employeeEntity.getId());

    Team createdTeam = teamService.create(team);

    assertEquals(1, teamRepository.count());

    assertNotNull(createdTeam.getId());
    assertEquals(team.getName(), createdTeam.getName());
    assertEquals(team.getTeamLeadId(), createdTeam.getTeamLeadId());
  }

  @Test
  void createWithoutTeamLead() {
    Team team = team();
    team.setTeamLeadId(null);

    Team createdTeam = teamService.create(team);

    assertEquals(1, teamRepository.count());

    assertNotNull(createdTeam.getId());
    assertEquals(team.getName(), createdTeam.getName());
    assertNull(createdTeam.getTeamLeadId());
  }

  @Test
  void createWithNonExistingTeamLead() {
    Team team = team();

    HrmException exception = assertThrows(HrmException.class, () -> teamService.create(team));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);
  }

  @Test
  void read() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());
    TeamEntity teamEntity = teamRepository.save(teamEntity(employeeEntity));

    Team readTeam = teamService.read(teamEntity.getId());

    assertEquals(teamEntity.getId(), readTeam.getId());
    assertEquals(teamEntity.getName(), readTeam.getName());
    assertEquals(teamEntity.getTeamLead().getId(), readTeam.getTeamLeadId());
  }

  @Test
  void readNonExistingTeam() {
    HrmException exception = assertThrows(HrmException.class, () -> teamService.read(TEAM_ID));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);
  }

  @Test
  void update() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());
    TeamEntity teamEntity = teamRepository.save(teamEntity(employeeEntity));

    EmployeeEntity newEmployeeEntity = employeeRepository.save(employeeEntity());

    Team teamUpdate = team();
    teamUpdate.setName("New name");
    teamUpdate.setTeamLeadId(newEmployeeEntity.getId());

    Team updatedTeam = teamService.update(teamEntity.getId(), teamUpdate);

    assertEquals(teamUpdate.getId(), updatedTeam.getId());
    assertEquals(teamUpdate.getName(), updatedTeam.getName());
    assertEquals(teamUpdate.getTeamLeadId(), updatedTeam.getTeamLeadId());
  }

  @Test
  void updateWithNoTeamLead() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());
    TeamEntity teamEntity = teamRepository.save(teamEntity(employeeEntity));

    Team teamUpdate = team();
    teamUpdate.setName("New name");
    teamUpdate.setTeamLeadId(null);

    Team updatedTeam = teamService.update(teamEntity.getId(), teamUpdate);

    assertEquals(teamUpdate.getId(), updatedTeam.getId());
    assertEquals(teamUpdate.getName(), updatedTeam.getName());
    assertNull(updatedTeam.getTeamLeadId());
  }

  @Test
  void updateNonExistingTeam() {
    Team teamUpdate = team();

    HrmException exception =
        assertThrows(HrmException.class, () -> teamService.update(TEAM_ID, teamUpdate));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);
  }

  @Test
  void updateWithNonExistingTeamLead() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());
    TeamEntity teamEntity = teamRepository.save(teamEntity(employeeEntity));

    Team teamUpdate = team();
    teamUpdate.setName("New name");
    teamUpdate.setTeamLeadId(99999);

    HrmException exception =
        assertThrows(HrmException.class, () -> teamService.update(teamEntity.getId(), teamUpdate));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);
  }

  @Test
  void delete() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());

    assertTrue(teamRepository.findById(teamEntity.getId()).isPresent());

    teamService.delete(teamEntity.getId());

    assertFalse(teamRepository.findById(teamEntity.getId()).isPresent());
  }

  @Test
  void deleteNonExistingTeam() {
    HrmException exception = assertThrows(HrmException.class, () -> teamService.delete(TEAM_ID));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);
  }
}
