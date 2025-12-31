package com.daviipkp.lifedashboard.latest.instance;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Streak {

    String name;
    short count;
    boolean doneToday;

    public Streak() {
    }
}
