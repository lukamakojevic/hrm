package com.hyperoptic.homework.hrm.models;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class Employee {
    private Integer id;

    @NotNull
    private String name;

    private Integer teamId;
}
