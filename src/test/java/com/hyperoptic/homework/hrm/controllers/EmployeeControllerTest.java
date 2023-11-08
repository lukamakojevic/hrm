package com.hyperoptic.homework.hrm.controllers;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperoptic.homework.hrm.models.Employee;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
import com.hyperoptic.homework.hrm.services.EmployeeService;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  public static final String BASE_PATH = "/api/employees";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper mapper;

  @MockBean private EmployeeService employeeService;

  @Test
  void create() throws Exception {
    Employee employee = employee();

    URI uri = URI.create(BASE_PATH);

    RequestBuilder request =
        MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(employee));

    mockMvc.perform(request).andExpect(status().isCreated());

    verify(employeeService).create(employee);
  }

  @ParameterizedTest
  @MethodSource("invalidNameTestCases")
  void createWithInvalidName(String invalidName) throws Exception {
    Employee employee = employee();
    employee.setName(invalidName);

    URI uri = URI.create(BASE_PATH);

    RequestBuilder request =
        MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(employee));

    mockMvc.perform(request).andExpect(status().isBadRequest());

    verifyNoInteractions(employeeService);
  }

  @Test
  void read() throws Exception {
    URI uri =
        UriComponentsBuilder.fromPath(BASE_PATH + "/{id}").buildAndExpand(EMPLOYEE_ID).toUri();

    RequestBuilder request = MockMvcRequestBuilders.get(uri);

    mockMvc.perform(request).andExpect(status().isOk());

    verify(employeeService).read(EMPLOYEE_ID);
  }

  @Test
  void readWithFilters() throws Exception {
    EmployeeSearchParams searchParams =
        EmployeeSearchParams.builder()
            .names(List.of(EMPLOYEE_NAME))
            .teamNames(List.of(TEAM_NAME))
            .leadingTeamNames(List.of(TEAM_NAME))
            .build();

    URI uri =
        UriComponentsBuilder.fromPath(BASE_PATH)
            .queryParam("names", List.of(EMPLOYEE_NAME))
            .queryParam("teamNames", List.of(TEAM_NAME))
            .queryParam("leadingTeamNames", List.of(TEAM_NAME))
            .buildAndExpand()
            .toUri();

    RequestBuilder request = MockMvcRequestBuilders.get(uri);

    mockMvc.perform(request).andExpect(status().isOk());

    verify(employeeService).read(searchParams);
  }

  @Test
  void update() throws Exception {
    Employee employee = employee();

    URI uri =
        UriComponentsBuilder.fromPath(BASE_PATH + "/{id}").buildAndExpand(EMPLOYEE_ID).toUri();

    RequestBuilder request =
        MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(employee));

    mockMvc.perform(request).andExpect(status().isOk());

    verify(employeeService).update(EMPLOYEE_ID, employee);
  }

  @ParameterizedTest
  @MethodSource("invalidNameTestCases")
  void updateWithInvalidName(String invalidName) throws Exception {
    Employee employee = employee();
    employee.setName(invalidName);

    URI uri =
        UriComponentsBuilder.fromPath(BASE_PATH + "/{id}").buildAndExpand(EMPLOYEE_ID).toUri();

    RequestBuilder request =
        MockMvcRequestBuilders.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(employee));

    mockMvc.perform(request).andExpect(status().isBadRequest());

    verifyNoInteractions(employeeService);
  }

  @Test
  void delete() throws Exception {
    URI uri =
        UriComponentsBuilder.fromPath(BASE_PATH + "/{id}").buildAndExpand(EMPLOYEE_ID).toUri();

    RequestBuilder request = MockMvcRequestBuilders.delete(uri);

    mockMvc.perform(request).andExpect(status().isOk());

    verify(employeeService).delete(EMPLOYEE_ID);
  }

  static Stream<Arguments> invalidNameTestCases() {
    return Stream.of(arguments((Object) null), arguments(" "), arguments(""));
  }
}
