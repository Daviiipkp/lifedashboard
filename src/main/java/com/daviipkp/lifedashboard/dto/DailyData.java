package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "tb_daily_data")
public class DailyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ElementCollection
    @CollectionTable(name = "daily_streaks", joinColumns = @JoinColumn(name = "daily_data_id"))
    @MapKeyColumn(name = "habit_name")
    @Column(name = "streak_value")
    private Map<String, Integer> streaks = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "daily_habits_bool", joinColumns = @JoinColumn(name = "daily_data_id"))
    @MapKeyColumn(name = "habit_name")
    @Column(name = "is_done")
    private Map<String, Boolean> dailyHabitsboolean = new HashMap<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<Float, String>> dailyHabitsCountable = new HashMap<>();

    public DailyData() {
    }

    public DailyData(Date date) {
        this.date = date;
    }

    public void setLeetCodeSolved(Integer today) {
        if (this.streaks == null) this.streaks = new HashMap<>();
        this.streaks.put("leetcode", today);
    }

    public void setDuolingoSolved(Integer today) {
        if (this.streaks == null) this.streaks = new HashMap<>();
        this.streaks.put("duolingo", today);
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Map<String, Integer> getStreaks() { return streaks; }
    public void setStreaks(Map<String, Integer> streaks) { this.streaks = streaks; }

    public Map<String, Map<Float, String>> getDailyHabitsCountable() { return dailyHabitsCountable; }
    public void setDailyHabitsCountable(Map<String, Map<Float, String>> dailyHabitsCountable) { this.dailyHabitsCountable = dailyHabitsCountable; }

    public Map<String, Boolean> getDailyHabitsboolean() { return dailyHabitsboolean; }
    public void setDailyHabitsboolean(Map<String, Boolean> dailyHabitsboolean) { this.dailyHabitsboolean = dailyHabitsboolean; }
}