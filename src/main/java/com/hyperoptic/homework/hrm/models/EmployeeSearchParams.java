package com.hyperoptic.homework.hrm.models;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class EmployeeSearchParams {
    private final List<String> names;
    private final List<String> teamNames;
    private final List<String> leadingTeamNames;
}
