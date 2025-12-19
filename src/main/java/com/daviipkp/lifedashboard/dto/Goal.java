package com.daviipkp.lifedashboard.dto;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private boolean done;
    private LocalDate date;

    public void setDone(boolean arg0) {
        done = arg0;
    }

    public boolean isDone() {
        return done;
    }
}
