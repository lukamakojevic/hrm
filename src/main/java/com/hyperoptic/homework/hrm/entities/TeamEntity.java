package com.hyperoptic.homework.hrm.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_lead_id", referencedColumnName = "id", unique = true)
    private EmployeeEntity teamLead;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<EmployeeEntity> teamMembers;
}
