package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_streaks")
public class Streaks {

    @Id
    private Long id;

    List<String> name;
    List<Integer> num;
    List<LocalDate> date;

    public Streaks() {
    }

    public Streaks(Long id, List<String> streakName, List<Integer> streakSize, List<LocalDate> date) {
        this.id = id;
        this.name =  streakName;
        this.num = streakSize;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getStreaks() {
        return name;
    }

    public List<Integer> getStreaksValues() {
        return num;
    }

    public void setStreaksValues(List<Integer> streaks) {
        this.num = streaks;
    }

    public void setStreaks(List<String> arg0, List<Integer> arg1,  List<LocalDate> date) {
        this.name = arg0;
        this.num = arg1;
        this.date = date;
    }

    public LocalDate getDate(String streak) {
        if(!getStreaks().contains(streak)) {
            return null;
        }
        return date.get(getStreaks().indexOf(streak));
    }

    public void addLeetCode(int arg0) {
        name.add("leetCodeSolved");
        num.add(arg0);
    }

    public void addDuolingo(int arg0) {
        name.add("duolingoSolved");
        num.add(arg0);
    }

    public void setDate(String s, LocalDate date) {
        this.date.set(getStreaks().indexOf(s), date);
    }

}