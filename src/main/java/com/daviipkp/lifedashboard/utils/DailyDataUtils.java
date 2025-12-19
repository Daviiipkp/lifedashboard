package com.daviipkp.lifedashboard.utils;

import com.daviipkp.lifedashboard.dto.DailyData;

import java.util.List;
import java.util.Map;

public class DailyDataUtils {

    public static int dayValue(DailyData dailyData) {
        int habits = 9;

        final float META_SLEEP = 6.0f;
        final float META_WATER = 3.0f;
        final float META_LEETCODE = 1.0f;
        final float META_DUOLINGO = 1.0f;
        final float HORA_MAXIMA_ACORDAR = 7.5f;
        final float META_READING = 15;

        Map<String, Float> contaveis = dailyData.getDailyHabitsCountable();
        List<String> concluidos = dailyData.getDailyHabitsboolean();
        if (getSafeValue(contaveis, "sleep") < META_SLEEP) habits--;

        float horaAcordar = getSafeValue(contaveis, "wakeUpTime");
        if (horaAcordar > HORA_MAXIMA_ACORDAR || horaAcordar == 0.0f) habits--;

        if (getSafeValue(contaveis, "water") < META_WATER) habits--;

        if (getSafeValue(contaveis, "leetCodeSolved") < META_LEETCODE) habits--;
        if (getSafeValue(contaveis, "duolingoSolved") < META_DUOLINGO) habits--;

        if (getSafeValue(contaveis, "reading") < META_READING) habits--;
        if (getSafeValue(contaveis, "duolingoSolved") < META_DUOLINGO) habits--;

        if (!concluidos.contains("detox")) habits--;

        if(habits > 8) {
            return 3;
        }else if(habits > 5) {
            return 2;
        }else {
            return 1;
        }
    }

    private static float getSafeValue(Map<String, Float> map, String key) {
        return map != null ? map.getOrDefault(key, 0.0f) : 0.0f;
    }

}
