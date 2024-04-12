package com.hyperoptic.homework.hrm.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String name;

  @ManyToOne
  private TeamEntity team;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "teamLead")
  private TeamEntity leadingTeam;
}
