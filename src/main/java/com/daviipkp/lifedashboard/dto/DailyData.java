package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "tb_daily_data")
public class DailyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private List<String> booleanDailyHabitsDone = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Float> dailyHabitsCountable = new HashMap<>();

    public DailyData() {
    }

    public DailyData(LocalDate date) {
        this.date = date;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Map<String, Float> getDailyHabitsCountable() { return dailyHabitsCountable; }
    public void setDailyHabitsCountable(Map<String, Float> dailyHabitsCountable) { this.dailyHabitsCountable = dailyHabitsCountable; }

    public List<String> getDailyHabitsboolean() { return booleanDailyHabitsDone; }
    public void setDailyHabitsboolean(List<String> dailyHabitsboolean) { this.booleanDailyHabitsDone = dailyHabitsboolean; }
}