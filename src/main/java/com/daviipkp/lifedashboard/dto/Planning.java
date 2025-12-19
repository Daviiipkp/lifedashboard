package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "tb_planning")
public class Planning {
    @Id
    private LocalDate date;

    private String content;

    public Planning() {}

    public Planning(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }

    public LocalDate getDate() {return date;}
    public String getContent() {return content;}


}
