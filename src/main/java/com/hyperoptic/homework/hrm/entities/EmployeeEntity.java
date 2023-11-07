package com.hyperoptic.homework.hrm.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private TeamEntity team;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "teamLead")
    private TeamEntity teamLeadOf;
}
