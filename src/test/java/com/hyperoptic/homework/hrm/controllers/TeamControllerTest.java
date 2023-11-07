package com.hyperoptic.homework.hrm.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import com.hyperoptic.homework.hrm.services.TeamService;
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

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static com.hyperoptic.homework.hrm.TestUtils.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {

    public static final String BASE_PATH = "/api/teams";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TeamService teamService;

    @Test
    void create() throws Exception {
        Team team = team();

        URI uri = URI.create(BASE_PATH);

        RequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(team));

        mockMvc.perform(request).andExpect(status().isCreated());

        verify(teamService).create(team);
    }

    @ParameterizedTest
    @MethodSource("invalidNameTestCases")
    void createWithInvalidName(String invalidName) throws Exception {
        Team team = team();
        team.setName(invalidName);

        URI uri = URI.create(BASE_PATH);

        RequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(team));

        mockMvc.perform(request).andExpect(status().isBadRequest());

        verifyNoInteractions(teamService);
    }

    @Test
    void read() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(TEAM_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(teamService).read(TEAM_ID);
    }

    @Test
    void readWithFilters() throws Exception {
        TeamSearchParams searchParams = TeamSearchParams.builder()
                .names(List.of(TEAM_NAME))
                .teamLeadNames(List.of(EMPLOYEE_NAME))
                .teamMemberNames(List.of(EMPLOYEE_NAME))
                .build();

        URI uri = UriComponentsBuilder.fromPath(BASE_PATH)
                .queryParam("names", List.of(TEAM_NAME))
                .queryParam("teamLeadNames", List.of(EMPLOYEE_NAME))
                .queryParam("teamMemberNames", List.of(EMPLOYEE_NAME))
                .buildAndExpand()
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.get(uri);

        mockMvc.perform(request).andExpect(status().isOk());

        verify(teamService).read(searchParams);
    }

    @Test
    void update() throws Exception {
        Team team = team();

        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(TEAM_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(team));

        mockMvc.perform(request).andExpect(status().isOk());

        verify(teamService).update(TEAM_ID, team);
    }

    @ParameterizedTest
    @MethodSource("invalidNameTestCases")
    void updateWithInvalidName(String invalidName) throws Exception {
        Team team = team();
        team.setName(invalidName);

        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(TEAM_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(team));

        mockMvc.perform(request).andExpect(status().isBadRequest());

        verifyNoInteractions(teamService);
    }

    @Test
    void delete() throws Exception {
        URI uri = UriComponentsBuilder.fromPath(BASE_PATH + "/{id}")
                .buildAndExpand(TEAM_ID)
                .toUri();

        RequestBuilder request = MockMvcRequestBuilders.delete(uri);

        mockMvc.perform(request)
                .andExpect(status().isOk());

        verify(teamService).delete(TEAM_ID);
    }

    static Stream<Arguments> invalidNameTestCases() {
        return Stream.of(arguments((Object) null), arguments(" "), arguments(""));
    }
}