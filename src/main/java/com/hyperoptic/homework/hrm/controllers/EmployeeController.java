package com.hyperoptic.homework.hrm.controllers;

import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(value = "/employees")
    public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employee));
    }

    @GetMapping(value = "/employees")
    public ResponseEntity<List<Employee>> read(
            @RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<String> teamNames) {
        return ResponseEntity.ok(employeeService.read(names, teamNames));
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> read(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.read(id));
    }

    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody @Valid Employee employee) {
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
