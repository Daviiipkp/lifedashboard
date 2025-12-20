package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "userconfig")
public class UserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    float metaSleep;
    float maxWakeTime;
    float metaWater;
    int metaReading;
    int metaLeetCode;
    int metaDuolingo;

    int workoutDaysGoal;
    List<Integer> workoutSpecificDays;
    boolean enableDetox;
    boolean isDefault;

    public UserConfig() {}

    public UserConfig(Long ID, float metaSleep, float maxWakeTime, float metaWater, int metaReading, int metaLeetCode, int metaDuolingo, int workoutDaysGoal, List<Integer> workoutSpecificDays, boolean detox, boolean isDefault) {
        this.id = ID;
        this.metaSleep = metaSleep;
        this.maxWakeTime = maxWakeTime;
        this.metaWater = metaWater;
        this.metaReading = metaReading;
        this.metaLeetCode = metaLeetCode;
        this.metaDuolingo = metaDuolingo;
        this.workoutDaysGoal = workoutDaysGoal;
        this.workoutSpecificDays = workoutSpecificDays;
        this.enableDetox = enableDetox;
        this.isDefault = isDefault;
        if(isDefault) {
            System.out.println("DEFAULT SENT");
        }
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

    public boolean isDefault() {
        return isDefault;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id_) {id = id_;}

}
