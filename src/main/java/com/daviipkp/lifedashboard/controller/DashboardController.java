package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.DailyLogDTO;
import com.daviipkp.lifedashboard.dto.Streaks;
import com.daviipkp.lifedashboard.dto.UserConfig;
import com.daviipkp.lifedashboard.repository.ConfigRepository;
import com.daviipkp.lifedashboard.repository.DailyDataRepository;
import com.daviipkp.lifedashboard.repository.GoalRepository;
import com.daviipkp.lifedashboard.repository.StreaksRepository;
import com.daviipkp.lifedashboard.service.LeetCodeService;
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


    @PostMapping("/log")
    public DailyData createEntry(@RequestBody DailyLogDTO log) {
        Integer solvedTodayLC = leetCodeService.getSolvedCount();
        Integer solvedTodayDUO = 0;
        DailyData data = getDataByDate(log.date());
        if(data == null) {
            data = new DailyData();
        }
        Streaks dto = streaksRepository.findById(0l).orElse(null);
        List<Integer> l = dto.getStreaksValues();
        if(dto != null) {
            List<String> ss = dto.getStreaks();
            for(String s : Transforming.checkChanges(log)) {
                //add if conditionnnnnnnnnnnnnnn
                    if(dto.getDate(s) == null) {
                        dto.setDate(s, LocalDate.now());
                        int num = l.get(ss.indexOf(s));
                        l.set(ss.indexOf(s), 1);
                    }
                    if(ss.contains(s) && !Objects.equals(dto.getDate(s), LocalDate.now())) {
                        int num = l.get(ss.indexOf(s));
                        l.set(ss.indexOf(s), num + 1);
                    }

            }
        }
        dto.setStreaksValues(l);
        streaksRepository.save(dto);
        return repository.save(Transforming.incorporateDailyLog(log, data));
    }

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

    @GetMapping("/streaks")
    public Streaks getStreaks(@RequestParam Long id) {
        Streaks dto = streaksRepository.findById(id).orElse(null);
        Integer solvedTodayLC = leetCodeService.getSolvedCount();
        Integer solvedTodayDUO = 0;

        List<String> l = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        if(dto == null) {
            dto = getClearStreaks(id);
            streaksRepository.save(dto);
       }else{
            for(String s : dto.getStreaks()) {
                if(dto.getDate(s) != LocalDate.now() && dto.getDate(s) != LocalDate.now().minusDays(1)) {
                    dto.setStreakValue(s, 0);
                }
            }
        }
        dto.addDuolingo(solvedTodayDUO);
        dto.addLeetCode(solvedTodayLC);

        return dto;
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