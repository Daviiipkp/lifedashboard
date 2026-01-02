package com.daviipkp.lifedashboard.latest.dto.api;

import jakarta.persistence.Embeddable;

@Embeddable
public record StreaksData(StreakWidgetProps[] streaks) {
}
