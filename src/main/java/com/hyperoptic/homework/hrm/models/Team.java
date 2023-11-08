package com.hyperoptic.homework.hrm.models;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Team {
  @Schema(example = "123")
  private Integer id;

  @NotBlank
  @Schema(description = "Name of the team", example = "Development")
  private String name;

  @Schema(description = "The id of the employee who is the team lead", example = "456")
  private Integer teamLeadId;
}
