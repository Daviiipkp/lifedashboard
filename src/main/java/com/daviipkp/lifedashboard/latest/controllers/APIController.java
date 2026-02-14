package com.daviipkp.lifedashboard.latest.controllers;

import com.daviipkp.lifedashboard.latest.dto.api.*;
import com.daviipkp.lifedashboard.latest.instance.UserAuthData;
import com.daviipkp.lifedashboard.latest.instance.UserContentData;
import com.daviipkp.lifedashboard.latest.repositories.ContentRep;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import com.daviipkp.lifedashboard.latest.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class APIController {

    private final DashboardService dashboardService;
    private final UserRep contentRep;

    public APIController(DashboardService dashboardService, UserRep arg1) {
        this.contentRep = arg1;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/health")
    public String health() {
        StringBuilder b = new StringBuilder();
        for(UserAuthData c : contentRep.findAll()) {
            b.append(c.getUsername());
            b.append("\n");
        }
        return b.toString();
    }

    @GetMapping("/streaksdata")
    public ResponseEntity<StreaksData> getStreakData(@AuthenticationPrincipal UserAuthData user) {
        if (user == null || !user.isEnabled()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(dashboardService.getStreakData(user));
    }

    @PostMapping("/saveplanning")
    public ResponseEntity<Void> savePlanning(@AuthenticationPrincipal UserAuthData user,
                                             @RequestBody Map<String, String> payload) {
        dashboardService.savePlanning(user, payload.get("planning"));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/savelog")
    public ResponseEntity<StreaksData> saveLog(@AuthenticationPrincipal UserAuthData user,
                                               @RequestBody DailyLog log) {
        return ResponseEntity.ok(dashboardService.saveDailyLog(user, log));
    }

    @PostMapping("/updategoals")
    public ResponseEntity<Void> updateGoals(@AuthenticationPrincipal UserAuthData user,
                                               @RequestBody Goal[] goals) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dailydata")
    public ResponseEntity<DailyData> getDailyData(@AuthenticationPrincipal UserAuthData user) {
        return ResponseEntity.ok(dashboardService.getDailyDataFormatted(user));
    }

    @GetMapping("/getlog")
    public ResponseEntity<DailyLog> getLog(@AuthenticationPrincipal UserAuthData user) {
        return ResponseEntity.ok(dashboardService.getLogDto(user));
    }

    @GetMapping("/pertinentdata")
    public ResponseEntity<PertinentData> getPertinentData(@AuthenticationPrincipal UserAuthData user) {
        return ResponseEntity.ok(dashboardService.getPertinentData(user));
    }

    @GetMapping("/habitscfg")
    public ResponseEntity<HabitsConfig> getHabitsConfig(@AuthenticationPrincipal UserAuthData user) throws IllegalAccessException {
        var content = dashboardService.getOrCreateContent(user);
        return ResponseEntity.ok(content.getHabitsConfigDTO());
    }

    @GetMapping("/goals")
    public List<Goal> getGoals() {
        return Collections.emptyList();
    }

    @GetMapping("/dailythoughts")
    public String getDailyThoughts(LocalDate date) {
        return "";
    }

    @GetMapping("/dailyobservations")
    public List<String> getDailyObservations(LocalDate date) {
        return Collections.emptyList();
    }

    @GetMapping("/calendar")
    public List<DailyData> getCalendar(LocalDate month) {
        return Collections.emptyList();
    }

    @GetMapping("/dailyinsight")
    public String getDailyInsight() {
        return "";
    }

    @GetMapping("/ranking")
    public Map<String, RankingData> getRanking() {
        return Collections.emptyMap();
    }
}