package com.hyperoptic.homework.hrm.repositories;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import com.hyperoptic.homework.hrm.models.EmployeeSearchParams;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployees(EmployeeSearchParams searchParams) {
        List<String> names = searchParams.getNames();
        List<String> teamNames = searchParams.getTeamNames();
        List<String> leadingTeamNames = searchParams.getLeadingTeamNames();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> cq = cb.createQuery(EmployeeEntity.class);

        Root<EmployeeEntity> employeeEntity = cq.from(EmployeeEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(names) && !names.isEmpty()) {
            Predicate anyOfNamesLike = cb.or(names.stream()
                    .map(name -> cb.like(employeeEntity.get("name"), "%" + name + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfNamesLike);
        }

        if (nonNull(teamNames) && !teamNames.isEmpty()) {
            Predicate anyOfTeamNamesLike = cb.or(teamNames.stream()
                    .map(teamName -> cb.like(employeeEntity.get("team").get("name"), "%" + teamName + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfTeamNamesLike);
        }

        if (nonNull(leadingTeamNames) && !leadingTeamNames.isEmpty()) {
            Predicate anyOfLeadingTeamNamesLike = cb.or(leadingTeamNames.stream()
                    .map(leadingTeamName -> cb.like(employeeEntity.get("leadingTeam").get("name"), "%" + leadingTeamName + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfLeadingTeamNamesLike);
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Transactional(readOnly = true)
    public List<TeamEntity> findTeams(TeamSearchParams searchParams) {
        List<String> names = searchParams.getNames();
        List<String> teamLeadNames = searchParams.getTeamLeadNames();
        List<String> teamMemberNames = searchParams.getTeamMemberNames();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeamEntity> cq = cb.createQuery(TeamEntity.class);

        Root<TeamEntity> teamEntity = cq.from(TeamEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(names) && !names.isEmpty()) {
            Predicate anyOfNamesLike = cb.or(names.stream()
                    .map(name -> cb.like(teamEntity.get("name"), "%" + name + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfNamesLike);
        }

        if (nonNull(teamLeadNames) && !teamLeadNames.isEmpty()) {
            Predicate anyOfTeamLeadNamesLike = cb.or(teamLeadNames.stream()
                    .map(teamName -> cb.like(teamEntity.get("teamLead").get("name"), "%" + teamName + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfTeamLeadNamesLike);
        }

        if (nonNull(teamMemberNames) && !teamMemberNames.isEmpty()) {
            Join<TeamEntity, EmployeeEntity> teamMemberJoin = teamEntity.join("teamMembers", JoinType.INNER);

            Predicate anyOfTeamMemberNamesLike = cb.or(teamMemberNames.stream()
                    .map(memberName -> cb.like(teamMemberJoin.get("name"), "%" + memberName + "%"))
                    .toArray(Predicate[]::new));

            predicates.add(anyOfTeamMemberNamesLike);
        }

        cq.where(predicates.toArray(new Predicate[0])).distinct(true);

        return em.createQuery(cq).getResultList();
    }
}
