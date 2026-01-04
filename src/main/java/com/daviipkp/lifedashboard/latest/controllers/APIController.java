package com.daviipkp.lifedashboard.latest.controllers;

import com.daviipkp.lifedashboard.dto.DailyLogDTO;
import com.daviipkp.lifedashboard.dto.Streaks;
import com.daviipkp.lifedashboard.latest.dto.api.*;
import com.daviipkp.lifedashboard.latest.instance.DailyLogData;
import com.daviipkp.lifedashboard.latest.instance.UserAuthData;
import com.daviipkp.lifedashboard.latest.instance.UserContentData;
import com.daviipkp.lifedashboard.latest.repositories.ContentRep;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import com.daviipkp.lifedashboard.latest.services.DuolingoService;
import com.daviipkp.lifedashboard.latest.services.LeetCodeService;
import com.daviipkp.lifedashboard.utils.Transforming;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private ContentRep contentRepository;

    @Autowired
    private UserRep userRepository;
    @Autowired
    private LeetCodeService leetCodeService;

    @Autowired
    private DuolingoService duolingoService;

    @GetMapping("/streaksdata")
    public StreaksData getStreakData(@AuthenticationPrincipal UserAuthData user, HttpServletRequest request){
        if(user == null || !user.isEnabled()) {
            return null;
        }
        UserContentData data = contentRepository.findByID(user.getContentID()).orElse(null);
        if(data != null) {
            data = new UserContentData();
            contentRepository.save(data);
            user.setContentID(data.getID());
            userRepository.save(user);
        }
        return data.getStreaksData();
    }

    @GetMapping("/goals")
    public List<Goal> getGoals() {
        return null;
    }

    @GetMapping("/dailytoughts")
    public String getDailyThoughts(LocalDate date) {
        return null;
    }

    @GetMapping("/dailyobservations")
    public List<String> getDailyObservations(LocalDate date) {
        return null;
    }

    @GetMapping("/calendar")
    public List<DailyData> getCalendar(LocalDate month) {
        //Don't forget that you must get it by saving the first month that is registered in database and using a sequential ID for the other months.
        //Ex: Logged in January 1st so that's month 1. February would be month 2. No need to save dates on database, just sequence.
        return null;
    }

    @GetMapping("/dailyinsight")
    public String getDailyInsight() {
        return null;
    }

    @GetMapping("/ranking")
    public Map<String, RankingData> getRanking() {
        return null;
    }

    @PostMapping("/log")
    public ResponseEntity<Object> createEntry(@AuthenticationPrincipal UserAuthData user, @RequestBody DailyLogDTO log) {
        Integer solvedTodayLC = leetCodeService.getStreakCount(user.getUsername());
        Integer solvedTodayDUO = duolingoService.getStreakCount(user.getUsername());
        UserContentData data = contentRepository.getById(user.getContentID());
        StreaksData strData = data.getStreaksData();
        if(strData.streaks() != null) {
            for(String s : Transforming.checkChanges(log)) {


            }
        }
        return null;
    }

    @GetMapping("/getlog")
    public ResponseEntity<DailyLog> getLog(@AuthenticationPrincipal UserAuthData user, HttpServletRequest request) {
        Integer solvedTodayLC = leetCodeService.getStreakCount(user.getUsername());
        Integer solvedTodayDUO = duolingoService.getStreakCount(user.getUsername());
        UserContentData data = contentRepository.getById(user.getContentID());
        List<DailyLogData> logs = data.getLogs();
        if(logs != null) {
            for(DailyLogData log : logs) {
                if(Objects.equals(log.getDate(), LocalDate.now())) {
                    return ResponseEntity.ok().body(new DailyLog(log.getWakeUpTime(),
                            log.getSleepTime(),
                            log.isWorkedOut(),
                            log.getMeals(),
                            log.getWaterIntake(),
                            log.isDetox(),
                            log.getReading(),
                            log.getStudying(),
                            log.getFocusLevel()));
                }
            }
        }
        return ResponseEntity.ok().body(new DailyLog(0d,
                0d,
                false,
                0,
                0,
                false,
                0,
                0,
                (short) 0));
    }


}
