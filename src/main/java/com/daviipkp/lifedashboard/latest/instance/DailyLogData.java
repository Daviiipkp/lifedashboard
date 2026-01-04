package com.daviipkp.lifedashboard.latest.instance;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class DailyLogData {

    private int waterIntake;
    private int meals;
    private double wakeUpTime;
    private double sleepTime;
    private boolean workedOut;
    private boolean detox;
    private Integer reading;
    private Integer studying;
    private short focusLevel;
    private LocalDate date;
}
