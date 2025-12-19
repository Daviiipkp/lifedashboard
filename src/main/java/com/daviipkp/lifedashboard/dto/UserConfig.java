package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "userconfig")
public class UserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    float metaSleep;
    float maxWakeTime;
    float metaWater;
    int metaReading;
    int metaLeetCode;
    int metaDuolingo;

    int workoutDaysGoal;
    List<Integer> workoutSpecificDays;
    boolean enableDetox;

    public UserConfig() {}

    public UserConfig(float arg0, float arg1, float arg2, int arg3, int arg4, int arg5, int arg6, List<Integer> arg7, boolean arg8) {
        metaSleep = arg0;
        maxWakeTime = arg1;
        metaWater = arg2;
        metaReading = arg3;
        metaLeetCode = arg4;
        metaDuolingo = arg5;
        workoutDaysGoal = arg6;
        workoutSpecificDays = arg7;
        enableDetox = arg8;
    }

    public float getMetaSleep() {
        return metaSleep;
    }

    public void setMetaSleep(float metaSleep) {
        this.metaSleep = metaSleep;
    }

    public float getMaxWakeTime() {
        return maxWakeTime;
    }

    public void setMaxWakeTime(float maxWakeTime) {
        this.maxWakeTime = maxWakeTime;
    }

    public float getMetaWater() {
        return metaWater;
    }

    public void setMetaWater(float metaWater) {
        this.metaWater = metaWater;
    }

    public int getMetaReading() {
        return metaReading;
    }

    public void setMetaReading(int metaReading) {
        this.metaReading = metaReading;
    }

    public int getMetaLeetCode() {
        return metaLeetCode;
    }

    public void setMetaLeetCode(int metaLeetCode) {
        this.metaLeetCode = metaLeetCode;
    }

    public int getMetaDuolingo() {
        return metaDuolingo;
    }

    public void setMetaDuolingo(int metaDuolingo) {
        this.metaDuolingo = metaDuolingo;
    }

    public int getWorkoutDaysGoal() {
        return workoutDaysGoal;
    }

    public void setWorkoutDaysGoal(int workoutDaysGoal) {
        this.workoutDaysGoal = workoutDaysGoal;
    }

    public List<Integer> getWorkoutSpecificDays() {
        return workoutSpecificDays;
    }

    public void setWorkoutSpecificDays(List<Integer> workoutSpecificDays) {
        this.workoutSpecificDays = workoutSpecificDays;
    }

    public boolean isEnableDetox() {
        return enableDetox;
    }

    public void setEnableDetox(boolean enableDetox) {
        this.enableDetox = enableDetox;
    }
}
