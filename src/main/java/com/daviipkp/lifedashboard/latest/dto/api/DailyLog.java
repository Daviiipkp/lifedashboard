package com.daviipkp.lifedashboard.latest.dto.api;

public record DailyLog(Double wakeUpTime,
        Double sleepTime,
        Boolean workedOut,
        Integer meals,
        Integer waterIntake,
        Boolean detox,
        Integer reading,
        Integer studying,
        Short focusLevel,
        Integer working) {
}
