package com.hyperoptic.homework.hrm.services;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.exceptions.HrmException;
import com.hyperoptic.homework.hrm.mappers.EmployeeMapper;
import com.hyperoptic.homework.hrm.mappers.EmployeeMapperImpl;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
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
class EmployeeServiceTest {

  @Mock private EmployeeRepository employeeRepository;
  @Mock private TeamRepository teamRepository;
  @Mock private SearchRepository searchRepository;
  @Mock private EmployeeMapper employeeMapper = new EmployeeMapperImpl();

  @InjectMocks private EmployeeService employeeService;

  @Test
  void create() {
    TeamEntity teamEntity = teamEntity();
    Employee employee = employee();

    EmployeeEntity employeeEntity = employeeEntity(teamEntity);

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity));
    when(employeeMapper.toEntity(employee, teamEntity)).thenReturn(employeeEntity);
    when(employeeRepository.saveAndFlush(employeeEntity)).thenReturn(employeeEntity);
    when(employeeMapper.toDto(employeeEntity)).thenReturn(employee);

    Employee createdEmployee = employeeService.create(employee);

    assertEquals(employee, createdEmployee);

    verify(teamRepository).findById(TEAM_ID);
    verify(employeeMapper).toEntity(employee, teamEntity);
    verify(employeeRepository).saveAndFlush(employeeEntity);
    verify(employeeMapper).toDto(employeeEntity);
  }

  @Test
  void createWithoutTeam() {
    Employee employee = employee();
    employee.setTeamId(null);

    EmployeeEntity employeeEntity = employeeEntity();

    when(employeeRepository.saveAndFlush(employeeEntity)).thenReturn(employeeEntity);
    when(employeeMapper.toEntity(employee, null)).thenReturn(employeeEntity);
    when(employeeMapper.toDto(employeeEntity)).thenReturn(employee);

    Employee createdEmployee = employeeService.create(employee);

    assertEquals(employee, createdEmployee);

    verify(employeeMapper).toEntity(employee, null);
    verify(employeeRepository).saveAndFlush(employeeEntity);
    verify(employeeMapper).toDto(employeeEntity);
  }

  @Test
  void createWithNonExistingTeam() {
    Employee employee = employee();

    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.create(employee));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);

    verify(teamRepository).findById(TEAM_ID);
    verifyNoInteractions(employeeMapper);
    verifyNoInteractions(employeeRepository);
  }

  @Test
  void readWithFilters() {
    EmployeeSearchParams searchParams = EmployeeSearchParams.builder().build();
    List<EmployeeEntity> employeeEntities = List.of(employeeEntity());
    List<Employee> employees = List.of(employee());

    when(searchRepository.findEmployees(searchParams)).thenReturn(employeeEntities);
    when(employeeMapper.toDtos(employeeEntities)).thenReturn(employees);

    List<Employee> result = employeeService.read(searchParams);

    assertEquals(employees, result);

    verify(searchRepository).findEmployees(searchParams);
    verify(employeeMapper).toDtos(employeeEntities);
  }

  @Test
  void read() {
    Employee employee = employee();
    EmployeeEntity employeeEntity = employeeEntity();

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));
    when(employeeMapper.toDto(employeeEntity)).thenReturn(employee);

    Employee readEmployee = employeeService.read(EMPLOYEE_ID);

    assertEquals(employee, readEmployee);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(employeeMapper).toDto(employeeEntity);
  }

  @Test
  void readNonExistingEmployee() {
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.read(EMPLOYEE_ID));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoInteractions(employeeMapper);
  }

  @Test
  void update() {
    TeamEntity teamEntity = teamEntity();
    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");

    EmployeeEntity employeeEntity = employeeEntity(teamEntity);
    employeeEntity.setName("New name");

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity()));
    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(teamEntity));
    when(employeeMapper.toEntity(employeeUpdate, teamEntity)).thenReturn(employeeEntity);
    when(employeeRepository.saveAndFlush(employeeEntity)).thenReturn(employeeEntity);
    when(employeeMapper.toDto(employeeEntity)).thenReturn(employeeUpdate);

    Employee updatedEmployee = employeeService.update(EMPLOYEE_ID, employeeUpdate);

    assertEquals(employeeUpdate, updatedEmployee);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(teamRepository).findById(TEAM_ID);
    verify(employeeMapper).toEntity(employeeUpdate, teamEntity);
    verify(employeeRepository).saveAndFlush(employeeEntity);
    verify(employeeMapper).toDto(employeeEntity);
  }

  @Test
  void updateWithNoTeam() {
    Employee employeeUpdate = employee();
    employeeUpdate.setTeamId(null);
    employeeUpdate.setName("New name");

    EmployeeEntity employeeEntity = employeeEntity();
    employeeEntity.setName("New name");

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity()));
    when(employeeMapper.toEntity(employeeUpdate, null)).thenReturn(employeeEntity);
    when(employeeRepository.saveAndFlush(employeeEntity)).thenReturn(employeeEntity);
    when(employeeMapper.toDto(employeeEntity)).thenReturn(employeeUpdate);

    Employee updatedEmployee = employeeService.update(EMPLOYEE_ID, employeeUpdate);

    assertEquals(employeeUpdate, updatedEmployee);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoInteractions(teamRepository);
    verify(employeeMapper).toEntity(employeeUpdate, null);
    verify(employeeRepository).saveAndFlush(employeeEntity);
    verify(employeeMapper).toDto(employeeEntity);
  }

  @Test
  void updateNonExistingEmployee() {
    TeamEntity teamEntity = teamEntity();
    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");

    EmployeeEntity employeeEntity = employeeEntity(teamEntity);
    employeeEntity.setName("New name");

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.update(EMPLOYEE_ID, employeeUpdate));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoMoreInteractions(employeeRepository);
    verifyNoInteractions(teamRepository);
    verifyNoInteractions(employeeMapper);
  }

  @Test
  void updateWithNonExistingTeam() {
    TeamEntity teamEntity = teamEntity();
    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");

    EmployeeEntity employeeEntity = employeeEntity(teamEntity);
    employeeEntity.setName("New name");

    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity()));
    when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.update(EMPLOYEE_ID, employeeUpdate));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(teamRepository).findById(TEAM_ID);
    verifyNoMoreInteractions(employeeRepository);
    verifyNoInteractions(employeeMapper);
  }

  @Test
  void delete() {
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity()));

    employeeService.delete(EMPLOYEE_ID);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verify(employeeRepository).deleteById(EMPLOYEE_ID);
  }

  @Test
  void deleteNonExistingEmployee() {
    when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.delete(EMPLOYEE_ID));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);

    verify(employeeRepository).findById(EMPLOYEE_ID);
    verifyNoMoreInteractions(employeeRepository);
  }
}
