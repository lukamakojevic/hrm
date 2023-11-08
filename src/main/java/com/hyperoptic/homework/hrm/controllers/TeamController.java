package com.hyperoptic.homework.hrm.controllers;

import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import com.hyperoptic.homework.hrm.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "REST endpoints for managing teams")
@RequestMapping(value = "/api/teams")
public class TeamController {

  private final TeamService teamService;

  @Operation(summary = "Create new team")
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Team> create(@RequestBody @Valid Team team) {
    return ResponseEntity.status(HttpStatus.CREATED).body(teamService.create(team));
  }

  @Operation(summary = "Read teams with search options")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Team>> read(
      @RequestParam(required = false) @Parameter(description = "Search by any of listed team names")
          List<String> names,
      @RequestParam(required = false)
          @Parameter(description = "Search by any of listed employee names")
          List<String> teamLeadNames,
      @RequestParam(required = false)
          @Parameter(description = "Search by any of listed employees names")
          List<String> teamMemberNames) {

    TeamSearchParams searchParams =
        TeamSearchParams.builder()
            .names(names)
            .teamLeadNames(teamLeadNames)
            .teamMemberNames(teamMemberNames)
            .build();

    return ResponseEntity.ok(teamService.read(searchParams));
  }

  @Operation(summary = "Read existing team")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Team> read(
      @PathVariable @Parameter(description = "Id of existing team") Integer id) {
    return ResponseEntity.ok(teamService.read(id));
  }

  @Operation(summary = "Update existing team")
  @PutMapping(
      value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Team> update(
      @PathVariable @Parameter(description = "Id of existing team") Integer id,
      @RequestBody @Valid Team team) {
    return ResponseEntity.ok(teamService.update(id, team));
  }

  @Operation(summary = "Delete existing team")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable @Parameter(description = "Id of existing team") Integer id) {
    teamService.delete(id);
    return ResponseEntity.ok().build();
  }
}
