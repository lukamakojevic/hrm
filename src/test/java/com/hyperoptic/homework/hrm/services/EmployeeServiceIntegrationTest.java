package com.hyperoptic.homework.hrm.services;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.exceptions.HrmException;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceIntegrationTest {

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private TeamRepository teamRepository;

  @Autowired private EmployeeService employeeService;

  @BeforeEach
  void setUp() {
    employeeRepository.deleteAll();
    employeeRepository.flush();
    teamRepository.deleteAll();
  }

  @Test
  void create() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());

    Employee employee = employee();
    employee.setTeamId(teamEntity.getId());

    Employee createdEmployee = employeeService.create(employee);

    assertEquals(1, employeeRepository.count());

    assertNotNull(createdEmployee.getId());
    assertEquals(employee.getName(), createdEmployee.getName());
    assertEquals(employee.getTeamId(), createdEmployee.getTeamId());
  }

  @Test
  void createWithoutTeam() {
    Employee employee = employee();
    employee.setTeamId(null);

    Employee createdEmployee = employeeService.create(employee);

    assertEquals(1, employeeRepository.count());

    assertNotNull(createdEmployee.getId());
    assertEquals(employee.getName(), createdEmployee.getName());
    assertNull(createdEmployee.getTeamId());
  }

  @Test
  void createWithNonExistingTeam() {
    Employee employee = employee();

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.create(employee));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);
  }

  @Test
  void read() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity(teamEntity));

    Employee readEmployee = employeeService.read(employeeEntity.getId());

    assertEquals(employeeEntity.getId(), readEmployee.getId());
    assertEquals(employeeEntity.getName(), readEmployee.getName());
    assertEquals(employeeEntity.getTeam().getId(), readEmployee.getTeamId());
  }

  @Test
  void readNonExistingEmployee() {
    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.read(EMPLOYEE_ID));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);
  }

  @Test
  void update() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity(teamEntity));

    TeamEntity newTeamEntity = teamRepository.save(teamEntity());

    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");
    employeeUpdate.setTeamId(newTeamEntity.getId());

    Employee updatedEmployee = employeeService.update(employeeEntity.getId(), employeeUpdate);

    assertEquals(employeeUpdate.getId(), updatedEmployee.getId());
    assertEquals(employeeUpdate.getName(), updatedEmployee.getName());
    assertEquals(employeeUpdate.getTeamId(), updatedEmployee.getTeamId());
  }

  @Test
  void updateWithNoTeam() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity(teamEntity));

    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");
    employeeUpdate.setTeamId(null);

    Employee updatedEmployee = employeeService.update(employeeEntity.getId(), employeeUpdate);

    assertEquals(employeeUpdate.getId(), updatedEmployee.getId());
    assertEquals(employeeUpdate.getName(), updatedEmployee.getName());
    assertNull(updatedEmployee.getTeamId());
  }

  @Test
  void updateNonExistingEmployee() {
    Employee employeeUpdate = employee();

    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.update(EMPLOYEE_ID, employeeUpdate));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);
  }

  @Test
  void updateWithNonExistingTeam() {
    TeamEntity teamEntity = teamRepository.save(teamEntity());
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity(teamEntity));

    Employee employeeUpdate = employee();
    employeeUpdate.setName("New name");
    employeeUpdate.setTeamId(99999);

    HrmException exception =
        assertThrows(
            HrmException.class,
            () -> employeeService.update(employeeEntity.getId(), employeeUpdate));

    assertEquals(ExceptionSupplier.TEAM_NOT_FOUND.get(), exception);
  }

  @Test
  void delete() {
    EmployeeEntity employeeEntity = employeeRepository.save(employeeEntity());

    assertTrue(employeeRepository.findById(employeeEntity.getId()).isPresent());

    employeeService.delete(employeeEntity.getId());

    assertFalse(employeeRepository.findById(employeeEntity.getId()).isPresent());
  }

  @Test
  void deleteNonExistingEmployee() {
    HrmException exception =
        assertThrows(HrmException.class, () -> employeeService.delete(EMPLOYEE_ID));

    assertEquals(ExceptionSupplier.EMPLOYEE_NOT_FOUND.get(), exception);
  }
}
