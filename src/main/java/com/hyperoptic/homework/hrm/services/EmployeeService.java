package com.hyperoptic.homework.hrm.services;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.mappers.EmployeeMapper;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final TeamRepository teamRepository;

    public Employee create(Employee employee) {
        TeamEntity teamEntity = teamRepository.findById(employee.getTeamId())
                .orElseThrow(ExceptionSupplier.NOT_FOUND);

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employee, teamEntity);

        return employeeMapper.toDto(employeeRepository.saveAndFlush(employeeEntity));
    }

    public List<Employee> read() {
        return employeeMapper.toDtos(employeeRepository.findAll());
    }

    public Employee read(Integer id) {
        return employeeMapper.toDto(employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.NOT_FOUND));
    }

    public Employee update(Integer id, Employee employee) {
        employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.NOT_FOUND);

        TeamEntity teamEntity = teamRepository.findById(employee.getTeamId())
                .orElseThrow(ExceptionSupplier.NOT_FOUND);

        employee.setId(id);
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employee, teamEntity);

        return employeeMapper.toDto(employeeRepository.saveAndFlush(employeeEntity));
    }

    public void delete(Integer id) {
        employeeRepository.findById(id)
                .orElseThrow(ExceptionSupplier.NOT_FOUND);

        employeeRepository.deleteById(id);
    }
}
