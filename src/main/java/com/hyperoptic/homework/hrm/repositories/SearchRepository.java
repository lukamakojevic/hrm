package com.hyperoptic.homework.hrm.repositories;

import com.hyperoptic.homework.hrm.entities.EmployeeEntity;
import com.hyperoptic.homework.hrm.entities.TeamEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<EmployeeEntity> findEmployees(List<String> names, List<String> teamNames) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> cq = cb.createQuery(EmployeeEntity.class);

        Root<EmployeeEntity> employeeEntity = cq.from(EmployeeEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(names) && !names.isEmpty()) {
            Predicate or = cb.or(names.stream()
                    .map(name -> cb.like(employeeEntity.get("name"), "%" + name + "%")).toArray(Predicate[]::new));

            predicates.add(or);
        }

        if (nonNull(teamNames) && !teamNames.isEmpty()) {
            Predicate or = cb.or(teamNames.stream()
                    .map(teamName -> cb.like(employeeEntity.get("team").get("name"), "%" + teamName + "%")).toArray(Predicate[]::new));

            predicates.add(or);
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }

    @Transactional(readOnly = true)
    public List<TeamEntity> findTeams(List<String> names, List<String> teamLeadNames) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TeamEntity> cq = cb.createQuery(TeamEntity.class);

        Root<TeamEntity> teamEntity = cq.from(TeamEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(names) && !names.isEmpty()) {
            Predicate or = cb.or(names.stream()
                    .map(name -> cb.like(teamEntity.get("name"), "%" + name + "%")).toArray(Predicate[]::new));

            predicates.add(or);
        }

        if (nonNull(teamLeadNames) && !teamLeadNames.isEmpty()) {
            Predicate or = cb.or(teamLeadNames.stream()
                    .map(teamName -> cb.like(teamEntity.get("teamLead").get("name"), "%" + teamName + "%")).toArray(Predicate[]::new));

            predicates.add(or);
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
}
