package com.hyperoptic.homework.hrm.models;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class Team {
    private Integer id;

    @NotNull
    private String name;

    private Integer teamLeadId;
}
