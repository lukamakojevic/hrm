package com.hyperoptic.homework.hrm.mappers;

import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.models.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "teamLeadId", source = "teamLead.id")
    Team toDto(TeamEntity teamEntity);

    List<Team> toDtos(List<TeamEntity> teamEntities);

    TeamEntity toEntity(Team team);

    List<TeamEntity> toEntities(List<Team> teams);
}