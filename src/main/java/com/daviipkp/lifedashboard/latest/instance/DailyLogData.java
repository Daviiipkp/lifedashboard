package com.daviipkp.lifedashboard.latest.instance;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
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
    private Integer working;

    @Lob
    private String planning;

    public DailyLogData() {
        this.date = LocalDate.now();
        this.waterIntake = 0;
        this.meals = 0;
        this.wakeUpTime = 0;
        this.sleepTime = 0;
        this.workedOut = false;
        this.detox = false;
        this.reading = 0;
        this.studying = 0;
        this.focusLevel = 0;
        this.working = 0;
        this.planning = "";
    }

    public short getDayValue() {
        int scoreWorkout = workedOut ? 10 : 0;
        int scoreDetox = detox ? 10 : 0;

        int scoreWater = Math.min(waterIntake / 200, 10);
        int scoreFocus = Math.min(focusLevel, 10);
        int scoreReading = Math.min(reading/3, 10);

        int scoreWork = (working >= 6) ? 10 : (working * 10 / 6);

        double weightedSum =
                        (scoreWorkout * 0.25) +
                        (scoreFocus   * 0.20) +
                        (scoreWater   * 0.15) +
                        (scoreWork    * 0.15) +
                        (scoreDetox   * 0.15) +
                        (scoreReading * 0.10);

        return (short) Math.round((weightedSum / 10 * 4) + 1);


    }

}
