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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @ManyToOne(cascade = CascadeType.REMOVE)
  private TeamEntity team;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "teamLead")
  private TeamEntity leadingTeam;
}
