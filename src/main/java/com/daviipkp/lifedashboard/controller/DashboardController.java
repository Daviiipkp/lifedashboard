package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.DailyLogDTO;
import com.daviipkp.lifedashboard.dto.Streaks;
import com.daviipkp.lifedashboard.repository.DailyDataRepository;
import com.daviipkp.lifedashboard.repository.StreaksRepository;
import com.daviipkp.lifedashboard.latest.services.LeetCodeService;
import com.daviipkp.lifedashboard.utils.DailyDataUtils;
import com.daviipkp.lifedashboard.utils.Transforming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardController {

    @Autowired
    private DailyDataRepository repository;

    @Autowired
    private StreaksRepository streaksRepository;

    @Autowired
    private LeetCodeService leetCodeService;


    @GetMapping("/by-date")
    public DailyData getData(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        DailyData data = getDataByDate(date);
        if(data == null) {
            data = new DailyData();
        }
        return data;
    }

    @GetMapping("/calendar")
    public Map<LocalDate, Integer> daysOfCalendar() {
        Map<LocalDate, Integer> l = new HashMap<>();
        for(DailyData data : getAll()) {
            l.put(data.getDate(), DailyDataUtils.dayValue(data));
        }
        return l;
    }

    @GetMapping("/day-value")
    public Integer dayValue(@RequestParam LocalDate date) {
        return DailyDataUtils.dayValue(getDataByDate(date));
    }

    @GetMapping
    public List<DailyData> getAll() {
        return repository.findAll();
    }

    public DailyData getDataByDate(LocalDate date
    ) {

        return repository.findByDate(date)
                .orElse(null);
    }

    public Streaks getClearStreaks(long id) {
        Streaks dto = new Streaks();
        List<LocalDate> today = new ArrayList<>();
        List<String> l = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();

        //initializes all streak last dates with null cause there's no last date
        for(int i = 0; i < 12; i++) {
            today.add(null);
        }
        String[] keys = {
                "sleep", "wakeUpTime", "workedOut", "focus", "water",
                "reading", "studying", "meals", "detox",
                "planning"
        };

        for (String key : keys) {
            l.add(key);
            l2.add(0);
        }

        dto = new Streaks(id, l, l2, today);
        dto.setStreaks(l,l2,today);
        return dto;

    }

    public void clearStreaks(long id) {
        streaksRepository.save(getClearStreaks(id));
    }

}