package com.daviipkp.lifedashboard.controller;

import com.daviipkp.lifedashboard.dto.UserConfig;
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
        return configRepository.findById(ID).orElse(new UserConfig(60*6f, 7.5f, 3.0f, 15, 1, 1, 3, l, false));
    }

    @PostMapping("/update-config")
    public void updateConfig(@RequestParam Long ID, @RequestBody UserConfig userConfig) {
        new DashboardController().clearStreaks(ID);
        configRepository.save(userConfig);
    }

    @GetMapping("/isConfigured")
    public boolean isConfigured(@RequestParam Long ID) {
        return configRepository.findById(ID).isPresent();
    }

}
