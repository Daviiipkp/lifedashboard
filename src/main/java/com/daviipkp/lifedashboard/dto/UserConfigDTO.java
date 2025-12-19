package com.daviipkp.lifedashboard.dto;

public record UserConfigDTO (int metaSleep,
        int maxWakeTime,
        int metaWater,
        int metaReading,
        int metaLeetCode,
        int metaDuolingo,

        int workoutDaysGoal,
        int workoutSpecificDays,
        boolean enableDetox){
}
