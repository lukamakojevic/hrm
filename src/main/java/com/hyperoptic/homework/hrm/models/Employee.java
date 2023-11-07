package com.hyperoptic.homework.hrm.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Employee {
    private Integer id;

    @NotBlank
    private String name;

    private Integer teamId;
}
