package com.hyperoptic.homework.hrm.services;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.exceptions.ExceptionSupplier;
import com.hyperoptic.homework.hrm.mappers.TeamMapper;
import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import com.hyperoptic.homework.hrm.repositories.EmployeeRepository;
import com.hyperoptic.homework.hrm.repositories.SearchRepository;
import com.hyperoptic.homework.hrm.repositories.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;
    private final SearchRepository searchRepository;

    private final TeamMapper teamMapper;

    public Team create(Team team) {
        EmployeeEntity employeeEntity = null;

        if (nonNull(team.getTeamLeadId())) {
            employeeEntity = employeeRepository.findById(team.getTeamLeadId())
                    .orElseThrow(ExceptionSupplier.EMPLOYEE_NOT_FOUND);

            if (nonNull(employeeEntity.getLeadingTeam())) {
                throw ExceptionSupplier.EMPLOYEE_TEAM_LEAD_ALREADY.get();
            }
        }

        TeamEntity teamEntity = teamMapper.toEntity(team, employeeEntity);

        return teamMapper.toDto(teamRepository.saveAndFlush(teamEntity));
    }

    public List<Team> read(TeamSearchParams searchParams) {
        return teamMapper.toDtos(searchRepository.findTeams(searchParams));
    }

    public Team read(Integer id) {
        return teamMapper.toDto(
                teamRepository.findById(id)
                .orElseThrow(ExceptionSupplier.TEAM_NOT_FOUND));
    }

    public Team update(Integer id, Team team) {
        teamRepository.findById(id)
                .orElseThrow(ExceptionSupplier.TEAM_NOT_FOUND);

        EmployeeEntity employeeEntity = null;

        if (nonNull(team.getTeamLeadId())) {
            employeeEntity = employeeRepository.findById(team.getTeamLeadId())
                    .orElseThrow(ExceptionSupplier.EMPLOYEE_NOT_FOUND);

            if (nonNull(employeeEntity.getLeadingTeam())) {
                throw ExceptionSupplier.EMPLOYEE_TEAM_LEAD_ALREADY.get();
            }
        }

        team.setId(id);
        TeamEntity teamEntity = teamMapper.toEntity(team, employeeEntity);

        return teamMapper.toDto(teamRepository.saveAndFlush(teamEntity));
    }

    public void delete(Integer id) {
        teamRepository.findById(id)
                .orElseThrow(ExceptionSupplier.TEAM_NOT_FOUND);

        teamRepository.deleteById(id);
    }
}
