package com.hyperoptic.homework.hrm.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringDocConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void openApiInfo() {
        assertEquals("Hyperoptic homework project documentation", openAPI.getInfo().getTitle());
        assertEquals("1.0.0", openAPI.getInfo().getVersion());
        assertEquals("This page contains REST API documentation for all available endpoints that exist in HRM project", openAPI.getInfo().getDescription());
        assertEquals("Luka Makojevic", openAPI.getInfo().getContact().getName());
        assertEquals("lukamakojevic@gmail.com", openAPI.getInfo().getContact().getEmail());
    }

    @Test
    void documentationAvailability() {
        String documentationUrl = "http://localhost:" + port + "/v3/api-docs/api";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(documentationUrl, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}