package com.hyperoptic.homework.hrm.controllers;

import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
import com.hyperoptic.homework.hrm.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController(value = "/api")
@RequiredArgsConstructor
@Tag(name = "REST endpoints for managing employees")
@RequestMapping(value = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Create new employee")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employee));
    }

    @Operation(summary = "Read employees with search options")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> read(
            @RequestParam(required = false)
            @Parameter(description = "Search by any of listed employee names") List<String> names,
            @RequestParam(required = false)
            @Parameter(description = "Search by any of listed team names") List<String> teamNames,
            @RequestParam(required = false)
            @Parameter(description = "Search by any of listed team names") List<String> leadingTeamNames) {

        EmployeeSearchParams searchParams = EmployeeSearchParams.builder()
                .names(names)
                .teamNames(teamNames)
                .leadingTeamNames(leadingTeamNames)
                .build();

        return ResponseEntity.ok(employeeService.read(searchParams));
    }

    @Operation(summary = "Read existing employee")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> read(@PathVariable @Parameter(description = "Id of existing employee") Integer id) {
        return ResponseEntity.ok(employeeService.read(id));
    }

    @Operation(summary = "Update existing employee")
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> update(@PathVariable @Parameter(description = "Id of existing employee") Integer id,
                                           @RequestBody @Valid Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @Operation(summary = "Delete existing employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Id of existing employee") Integer id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
