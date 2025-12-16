package com.daviipkp.lifedashboard.utils;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.DailyLog;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Transforming {

    public static DailyData incorporateDailyLog(DailyLog log, DailyData old) {
        Date date = null;
        if (log.date() != null) {
            date = Date.from(log.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        Map<String, Integer> streaks = new HashMap<>();
        Map<String, Map<Float, String>> countables = new HashMap<>();
        Map<String, Boolean> booleans = new HashMap<>();

        old.setDate(date);
        old.setStreaks(streaks);
        old.setDailyHabitsCountable(countables);
        old.setDailyHabitsboolean(booleans);

        return old;
    }

    private void addCountable(Map<String, Map<Float, String>> map,
                              String key,
                              Number value,
                              String unit) {
        if(value != null) {
            Map<Float, String> innerMap = new HashMap<>();
            innerMap.put(value.floatValue(), unit);
            map.put(key, innerMap);
        }

    }

    private void addBoolean(Map<String, Boolean> map,
                            String key,
                            Boolean value) {
        if(value != null) {
            map.put(key, value);
        }
    }

}
