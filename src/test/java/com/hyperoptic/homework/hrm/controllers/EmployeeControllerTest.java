package com.hyperoptic.homework.hrm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
import com.hyperoptic.homework.hrm.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    public static final String BASE_PATH = "/api/employees";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;


    @MockBean
    private EmployeeService employeeService;

    @Test
    void create() throws Exception {
        URI uri = URI.create(BASE_PATH);

        Employee employee = employee();

        RequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        verify(employeeService).create(employee);
    }

    @Test
    void read() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(EMPLOYEE_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(employeeService).read(EMPLOYEE_ID);
    }

    @Test
    void readWithFilters() throws Exception {
        EmployeeSearchParams searchParams = EmployeeSearchParams.builder()
                .names(List.of(EMPLOYEE_NAME))
                .teamNames(List.of(TEAM_NAME))
                .leadingTeamNames(List.of(TEAM_NAME))
                .build();

        URI uri = UriComponentsBuilder.fromPath(BASE_PATH)
                .queryParam("names", List.of(EMPLOYEE_NAME))
                .queryParam("teamNames", List.of(TEAM_NAME))
                .queryParam("leadingTeamNames", List.of(TEAM_NAME))
                .buildAndExpand()
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(employeeService).read(searchParams);
    }

    @Test
    void update() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(EMPLOYEE_ID)
                .toUri();

        Employee employee = employee();

        RequestBuilder request = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(employeeService).update(EMPLOYEE_ID, employee);
    }

    @Test
    void delete() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(EMPLOYEE_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.delete(uri);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(employeeService).delete(EMPLOYEE_ID);
    }
}