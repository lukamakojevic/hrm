package com.hyperoptic.homework.hrm.mappers;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "teamId", source = "team.id")
    Employee toDto(EmployeeEntity employeeEntity);

    List<Employee> toDtos(List<EmployeeEntity> employeeEntities);

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "team", source = "teamEntity")
    EmployeeEntity toEntity(Employee employee, TeamEntity teamEntity);
}

