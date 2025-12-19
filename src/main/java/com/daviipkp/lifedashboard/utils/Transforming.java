package com.daviipkp.lifedashboard.utils;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.DailyLogDTO;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

public class Transforming {

    public static DailyData incorporateDailyLog(DailyLogDTO log, DailyData old) {
        LocalDate date = LocalDate.now();
        Map<String, Float> countables = log.getCountable();
        List<String> booleanHabits = log.getUncountable();

        old.setDate(date);
        old.setDailyHabitsCountable(countables);
        old.setDailyHabitsboolean(booleanHabits);

        return old;
    }

    private void addCountable(Map<String, Map<Float, String>> map,
                              String key,
                              Number value,
                              String unit) {
        if (value != null) {
            Map<Float, String> innerMap = new HashMap<>();
            innerMap.put(value.floatValue(), unit);
            map.put(key, innerMap);
        }

    }

    private void addBoolean(Map<String, Boolean> map,
                            String key,
                            Boolean value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    public static List<String> checkChanges(DailyLogDTO log) {
        List<String> l = new ArrayList<>();
        for (Field field : log.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);

                String name = field.getName(); // ex: "sleepHours"
                Object value = field.get(log);       // ex: 7.5
                if (value != null) {
                    l.add(name);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(l.contains("date")) {
            l.remove("date");
        }
        return l;
    }

}
