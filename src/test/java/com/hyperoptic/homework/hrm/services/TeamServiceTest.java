package com.hyperoptic.homework.hrm.services;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.exceptions.HrmException;
import com.hyperoptic.homework.hrm.mappers.TeamMapper;
import com.hyperoptic.homework.hrm.mappers.TeamMapperImpl;
import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.SearchRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TeamServiceTest {

  @Mock private TeamRepository teamRepository;
  @Mock private EmployeeRepository employeeRepository;
  @Mock private SearchRepository searchRepository;
  @Mock private TeamMapper teamMapper = new TeamMapperImpl();

  @InjectMocks private TeamService teamService;

  @Test
  void create() {
    EmployeeEntity employeeEntity = employeeEntity();
    Team team = team();

    TeamEntity teamEntity = teamEntity(employeeEntity);

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));
    when(teamMapper.toEntity(team, employeeEntity)).thenReturn(teamEntity);
    when(teamRepository.saveAndFlush(teamEntity)).thenReturn(teamEntity);
    when(teamMapper.toDto(teamEntity)).thenReturn(team);

    Team createdTeam = teamService.create(team);

    assertEquals(team, createdTeam);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(teamMapper).toEntity(team, employeeEntity);
    verify(teamRepository).saveAndFlush(teamEntity);
    verify(teamMapper).toDto(teamEntity);
  }

  @Test
  void createWithoutTeamLead() {
    Team team = team();
    team.setTeamLeadId(null);

    TeamEntity teamEntity = teamEntity();

    when(teamRepository.saveAndFlush(teamEntity)).thenReturn(teamEntity);
    when(teamMapper.toEntity(team, null)).thenReturn(teamEntity);
    when(teamMapper.toDto(teamEntity)).thenReturn(team);

    Team createdTeam = teamService.create(team);

    assertEquals(team, createdTeam);

    verify(teamMapper).toEntity(team, null);
    verify(teamRepository).saveAndFlush(teamEntity);
    verify(teamMapper).toDto(teamEntity);
  }

  @Test
  void createWithNonExistingTeamLead() {
    Team team = team();

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

    HrmException exception = assertThrows(HrmException.class, () -> teamService.create(team));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoInteractions(teamMapper);
    verifyNoInteractions(teamRepository);
  }

  @Test
  void createWithInvalidTeamLead() {
    EmployeeEntity employeeEntity = employeeEntity();
    employeeEntity.setLeadingTeam(teamEntity());

    Team team = team();

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));

    HrmException exception = assertThrows(HrmException.class, () -> teamService.create(team));

    assertEquals(ExceptionSupplier.EMPLOYEE_TEAM_LEAD_ALREADY.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoInteractions(teamMapper);
    verifyNoInteractions(teamRepository);
  }

  @Test
  void readWithFilters() {
    TeamSearchParams searchParams = TeamSearchParams.builder().build();
    List<TeamEntity> teamEntities = List.of(teamEntity());
    List<Team> teams = List.of(team());

    when(searchRepository.findTeams(searchParams)).thenReturn(teamEntities);
    when(teamMapper.toDtos(teamEntities)).thenReturn(teams);

    List<Team> result = teamService.read(searchParams);

    assertEquals(teams, result);

    verify(searchRepository).findTeams(searchParams);
    verify(teamMapper).toDtos(teamEntities);
  }

  @Test
  void read() {
    Team team = team();
    TeamEntity teamEntity = teamEntity();

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity));
    when(teamMapper.toDto(teamEntity)).thenReturn(team);

    Team readTeam = teamService.read(TEAM_ID);

    assertEquals(team, readTeam);

    verify(teamRepository).findById(TEAM_ID);
    verify(teamMapper).toDto(teamEntity);
  }

  @Test
  void readNonExistingTeam() {
    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

    HrmException exception = assertThrows(HrmException.class, () -> teamService.read(TEAM_ID));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verifyNoInteractions(teamMapper);
  }

  @Test
  void update() {
    EmployeeEntity employeeEntity = employeeEntity();
    Team teamUpdate = team();
    teamUpdate.setName("New name");

    TeamEntity teamEntity = teamEntity(employeeEntity);
    teamEntity.setName("New name");

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity()));
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));
    when(teamMapper.toEntity(teamUpdate, employeeEntity)).thenReturn(teamEntity);
    when(teamRepository.saveAndFlush(teamEntity)).thenReturn(teamEntity);
    when(teamMapper.toDto(teamEntity)).thenReturn(teamUpdate);

    Team updatedTeam = teamService.update(TEAM_ID, teamUpdate);

    assertEquals(teamUpdate, updatedTeam);

    verify(teamRepository).findById(TEAM_ID);
    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(teamMapper).toEntity(teamUpdate, employeeEntity);
    verify(teamRepository).saveAndFlush(teamEntity);
    verify(teamMapper).toDto(teamEntity);
  }

  @Test
  void updateWithNoTeamLead() {
    Team teamUpdate = team();
    teamUpdate.setTeamLeadId(null);
    teamUpdate.setName("New name");

    TeamEntity teamEntity = teamEntity();
    teamEntity.setName("New name");

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity()));
    when(teamMapper.toEntity(teamUpdate, null)).thenReturn(teamEntity);
    when(teamRepository.saveAndFlush(teamEntity)).thenReturn(teamEntity);
    when(teamMapper.toDto(teamEntity)).thenReturn(teamUpdate);

    Team updatedTeam = teamService.update(TEAM_ID, teamUpdate);

    assertEquals(teamUpdate, updatedTeam);

    verify(teamRepository).findById(TEAM_ID);
    verifyNoInteractions(employeeRepository);
    verify(teamMapper).toEntity(teamUpdate, null);
    verify(teamRepository).saveAndFlush(teamEntity);
    verify(teamMapper).toDto(teamEntity);
  }

  @Test
  void updateNonExistingTeam() {
    EmployeeEntity employeeEntity = employeeEntity();
    Team teamUpdate = team();
    teamUpdate.setName("New name");

    TeamEntity teamEntity = teamEntity(employeeEntity);
    teamEntity.setName("New name");

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> teamService.update(TEAM_ID, teamUpdate));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verifyNoMoreInteractions(teamRepository);
    verifyNoInteractions(employeeRepository);
    verifyNoInteractions(teamMapper);
  }

  @Test
  void updateWithNonExistingEmployee() {
    EmployeeEntity employeeEntity = employeeEntity();
    Team teamUpdate = team();
    teamUpdate.setName("New name");

    TeamEntity teamEntity = teamEntity(employeeEntity);
    teamEntity.setName("New name");

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity()));
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> teamService.update(TEAM_ID, teamUpdate));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoMoreInteractions(teamRepository);
    verifyNoInteractions(teamMapper);
  }

  @Test
  void updateWithInvalidTeamLead() {
    EmployeeEntity employeeEntity = employeeEntity();
    employeeEntity.setLeadingTeam(teamEntity());

    Team teamUpdate = team();
    teamUpdate.setName("New name");

    TeamEntity teamEntity = teamEntity(employeeEntity);
    teamEntity.setName("New name");

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity));
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));

    HrmException exception =
        assertThrows(HrmException.class, () -> teamService.update(TEAM_ID, teamUpdate));

    assertEquals(ExceptionSupplier.EMPLOYEE_TEAM_LEAD_ALREADY.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoInteractions(teamMapper);
    verifyNoMoreInteractions(teamRepository);
  }

  @Test
  void delete() {
    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity()));

    teamService.delete(TEAM_ID);

    verify(teamRepository).findById(TEAM_ID);
    verify(teamRepository).deleteById(TEAM_ID);
  }

  @Test
  void deleteNonExistingTeam() {
    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

    HrmException exception = assertThrows(HrmException.class, () -> teamService.delete(TEAM_ID));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verifyNoMoreInteractions(teamRepository);
  }
}
