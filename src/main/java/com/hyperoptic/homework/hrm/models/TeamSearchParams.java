package com.hyperoptic.homework.hrm.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@EqualsAndHashCode
public class TeamSearchParams {
    private final List<String> names;
    private final List<String> teamLeadNames;
    private final List<String> teamMemberNames;
}