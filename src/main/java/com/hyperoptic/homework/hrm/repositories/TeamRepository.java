package com.hyperoptic.homework.hrm.repositories;

import com.hyperoptic.homework.hrm.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {}
