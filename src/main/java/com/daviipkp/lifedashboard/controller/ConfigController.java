package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.UserConfig;
import com.daviipkp.lifedashboard.dto.UserConfigDTO;
import com.daviipkp.lifedashboard.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/config")
@CrossOrigin("*")
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @GetMapping("/getuserconfig")
    public UserConfig getUserConfig(@RequestParam Long ID) {

        List<Integer> l = new ArrayList<>();
        l.add(2);l.add(4); l.add(6);
        if(isConfigured(ID)) {
            return configRepository.findById(ID).get();
        }else{
            return null;
        }
    }

    @PostMapping("/update-config")
    public void updateConfig(@RequestParam Long ID, @RequestBody UserConfigDTO userConfig) {
        //new DashboardController().clearStreaks(userConfig.getID());
        UserConfig c = new UserConfig(453l, userConfig.metaSleep(), userConfig.maxWakeTime(), userConfig.metaWater(), userConfig.metaReading(), userConfig.metaLeetCode(),
                userConfig.metaDuolingo(), userConfig.workoutDaysGoal(), userConfig.workoutSpecificDays(), userConfig.detox(), false);
        configRepository.save(c);
    }

    @GetMapping("/isConfigured")
    public boolean isConfigured(@RequestParam Long ID) {
        return configRepository.findById(ID).isPresent();
    }

}
