package com.daviipkp.lifedashboard.dto;

import java.util.List;

public record UserConfigDTO (Long ID, float metaSleep, float maxWakeTime, float metaWater, int metaReading, int metaLeetCode, int metaDuolingo, int workoutDaysGoal, List<Integer> workoutSpecificDays, boolean detox){
}
