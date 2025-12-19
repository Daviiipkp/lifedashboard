package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.Goal;
import com.daviipkp.lifedashboard.dto.Planning;
import com.daviipkp.lifedashboard.repository.GoalRepository;
import com.daviipkp.lifedashboard.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/planning")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlanningController {

    @Autowired
    private PlanningRepository repository;

    @GetMapping("/by-date")
    public Planning getPlanning(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return repository.findById(date).orElse(new Planning(date, ""));
    }

    @PostMapping
    public Planning savePlanning(@RequestBody Planning planning) {
        return repository.save(planning);
    }

}