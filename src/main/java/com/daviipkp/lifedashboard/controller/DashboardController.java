package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.DailyLog;
import com.daviipkp.lifedashboard.repository.DailyDataRepository;
import com.daviipkp.lifedashboard.service.LeetCodeService;
import com.daviipkp.lifedashboard.utils.Transforming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DailyDataRepository repository;

    @Autowired
    private LeetCodeService leetCodeService;

    @PostMapping("/log")
    public DailyData createEntry(@RequestBody DailyLog log) {
        Integer solvedTodayLC = leetCodeService.getSolvedCount();
        Integer solvedTodayDUO = 0;
        DailyData data = getDataByDate(log.date());
        if(data == null) {
            data = new DailyData();
        }

        return repository.save(Transforming.incorporateDailyLog(log, data));
    }

    @GetMapping()
    public DailyData getDailyData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Integer solvedTodayLC = leetCodeService.getSolvedCount();
        Integer  solvedTodayDUO = 0;
        DailyData data = getDataByDate(date);
        data.setLeetCodeSolved(solvedTodayLC);
        data.setDuolingoSolved(solvedTodayDUO);
        return data;
    }

    @GetMapping
    public List<DailyData> getAll() {
        return repository.findAll();
    }

    @GetMapping("/by-date") // URL ficará: /api/daily-logs/by-date?date=2025-12-15
    public DailyData getDataByDate(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {

        Date from = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date to = Date.from(date.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        return repository.findByDateBetween(from, to)
                .orElse(null);
    }

}