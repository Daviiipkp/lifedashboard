package com.daviipkp.lifedashboard.latest.instance;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GoalData {

    private String name;
    private String target;
    private Double progress;
    private String unit;

}
