package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.Goal;
import com.daviipkp.lifedashboard.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GoalController {

    @Autowired
    private GoalRepository repository;

    @GetMapping
    public List<Goal> getGoals(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return repository.findByDate(date);
    }

    @PostMapping
    public Goal createGoal(@RequestBody Goal goal) {
        return repository.save(goal);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleGoal(@PathVariable Long id) {
        return repository.findById(id).map(goal -> {
            goal.setDone(!goal.isDone());
            repository.save(goal);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
