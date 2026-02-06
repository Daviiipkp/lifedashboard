package com.daviipkp.lifedashboard.latest.dto.api;

public record StreakWidgetProps(String type, short count, boolean completedToday, StreakColors currentTheme) {
}
