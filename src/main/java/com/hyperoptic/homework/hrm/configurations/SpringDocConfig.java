package com.hyperoptic.homework.hrm.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

  public static final String BASE_PACKAGE = "com.hyperoptic.homework.hrm.controllers";

  @Bean
  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
        .group("api")
        .packagesToScan(BASE_PACKAGE)
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Hyperoptic homework project documentation")
                .version("1.0.0")
                .description(
                    "This page contains REST API documentation for all available endpoints that exist in HRM project")
                .contact((new Contact()).name("Luka Makojevic").email("lukamakojevic@gmail.com")));
  }
}
