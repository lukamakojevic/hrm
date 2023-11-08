package com.hyperoptic.homework.hrm.models;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
  @Schema(example = "789")
  private Integer id;

  @NotBlank
  @Schema(description = "Name of the employee", example = "Elon Musk")
  private String name;

  @Schema(description = "The id of the employee's team", example = "246")
  private Integer teamId;
}
