package com.daviipkp.lifedashboard.dto;

import lombok.Data;
import java.time.LocalDate;

public record DailyLog (LocalDate date,
                        Double sleepHours,
                        Double wakeUpTime,
                        Boolean workedOut,
                        Integer focusLevel,
                        Integer waterMl,
                        Integer readingMinutes,
                        Integer studyMinutes,
                        Short meals,
                        Boolean noSocialMedia,
                        String dailyPlan){
}