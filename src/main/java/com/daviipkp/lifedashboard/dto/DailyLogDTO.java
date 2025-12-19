package com.daviipkp.lifedashboard.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DailyLogDTO(LocalDate date,
                          Double sleep,
                          Double wakeUpTime,
                          Boolean workedOut,
                          Integer focus,
                          Integer water,
                          Integer reading,
                          Integer studying,
                          Short meals,
                          Boolean detox,
                          String planning){
    public Map<String, Float> getCountable() {
        Map<String, Float> map = new HashMap<>();
        map.put("sleep", this.sleep().floatValue());
        map.put("wakeUpTime", this.wakeUpTime().floatValue());
        map.put("focus", this.focus().floatValue());
        map.put("water", this.water().floatValue());
        map.put("reading", this.reading().floatValue());
        map.put("studying", this.studying().floatValue());
        map.put("meals", this.meals().floatValue());
        return map;
    }
    public List<String> getUncountable() {
        List<String> l = new ArrayList<>();
        if(workedOut()) {
            l.add("workedOut");
        }
        if(detox()) {
            l.add("detox");
        }
        return l;
    }
}