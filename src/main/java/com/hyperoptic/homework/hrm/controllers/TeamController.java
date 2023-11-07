package com.hyperoptic.homework.hrm.controllers;

import com.hyperoptic.homework.hrm.models.Team;
import com.hyperoptic.homework.hrm.models.TeamSearchParams;
import com.hyperoptic.homework.hrm.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping(value = "/teams")
    public ResponseEntity<Team> create(@RequestBody @Valid Team team) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.create(team));
    }

    @GetMapping(value = "/teams")
    public ResponseEntity<List<Team>> read(
            @RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<String> teamLeadNames,
            @RequestParam(required = false) List<String> teamMemberNames) {

        TeamSearchParams searchParams = TeamSearchParams.builder()
                .names(names)
                .teamLeadNames(teamLeadNames)
                .teamMemberNames(teamMemberNames)
                .build();

        return ResponseEntity.ok(teamService.read(searchParams));
    }

    @GetMapping(value = "/teams/{id}")
    public ResponseEntity<Team> read(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.read(id));
    }

    @PutMapping(value = "/teams/{id}")
    public ResponseEntity<Team> update(@PathVariable Integer id, @RequestBody @Valid Team team) {
        return ResponseEntity.ok(teamService.update(id, team));
    }

    @DeleteMapping(value = "/teams/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }
}
