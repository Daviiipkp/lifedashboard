package com.daviipkp.lifedashboard.latest.dto.api;

public record StreakColors(String main,
                           String outline,
                           String text,
                           String shadow,
                           String gradient) {

    public static StreakColors create(
            String color,
            String mainShade,
            String borderSpec,
            String textShade,
            String shadowSize,
            String gradientStart
    ) {
        return new StreakColors(
                "text-" + color + "-" + mainShade,
                "border-" + color + "-" + borderSpec,
                "text-" + color + "-" + textShade,
                "shadow-" + shadowSize,
                "from-" + color + "-" + gradientStart + " to-stone-950"
        );
    }
}
