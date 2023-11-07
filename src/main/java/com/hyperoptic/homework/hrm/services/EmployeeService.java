package com.hyperoptic.homework.hrm.services;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.mappers.EmployeeMapper;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.SearchRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final TeamRepository teamRepository;
    private final SearchRepository searchRepository;

    private final EmployeeMapper employeeMapper;

    public Employee create(Employee employee) {
        TeamEntity teamEntity = null;

        if (nonNull(employee.getTeamId())) {
            teamEntity = teamRepository.findById(employee.getTeamId())
                    .orElseThrow(ExceptionSupplier.BAD_REQUEST);
        }

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employee, teamEntity);

        return employeeMapper.toDto(employeeRepository.saveAndFlush(employeeEntity));
    }

    public List<Employee> read(List<String> names, List<String> teamNames) {
        return employeeMapper.toDtos(searchRepository.findEmployees(names, teamNames));
    }

    public Employee read(Integer id) {
        return employeeMapper.toDto(employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.BAD_REQUEST));
    }

    public Employee update(Integer id, Employee employee) {
        employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.BAD_REQUEST);

        TeamEntity teamEntity = null;

        if (nonNull(employee.getTeamId())) {
            teamEntity = teamRepository.findById(employee.getTeamId())
                    .orElseThrow(ExceptionSupplier.BAD_REQUEST);
        }

        employee.setId(id);
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employee, teamEntity);

        return employeeMapper.toDto(employeeRepository.saveAndFlush(employeeEntity));
    }

    public void delete(Integer id) {
        employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.BAD_REQUEST);

        employeeRepository.deleteById(id);
    }
}
