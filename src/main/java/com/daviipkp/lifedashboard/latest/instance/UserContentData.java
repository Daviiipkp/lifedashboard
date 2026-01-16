package com.daviipkp.lifedashboard.latest.instance;

import com.daviipkp.lifedashboard.latest.dto.api.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "usercontentdata")
@Getter
@Setter
@AllArgsConstructor
public class UserContentData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;

    @ElementCollection
    @CollectionTable(name = "daily_logs", joinColumns = @JoinColumn(name = "user_id"))
    private List<DailyLogData> logs = new ArrayList<>();

    @ElementCollection
    Map<String, Long> hbConfig = new HashMap<>();

    @ElementCollection
    private List<GoalData> goals = new ArrayList<>();

    private LocalDate lastStreaksUpdate;

    @ElementCollection
    private List<Streak> streaks = new ArrayList<>();

    public UserContentData() {
        List<String> defStreaks = List.of(
                "sleep", "wakeUpTime", "workedOut", "focus", "water",
                "reading", "studying", "meals", "detox",
                "planning"
        );
        for(int i = 0; i<defStreaks.size(); i++) {
            streaks.add(new Streak(defStreaks.get(i), (short)0, false));
        }
        for(Field f : HabitsConfig.class.getDeclaredFields()) {
            hbConfig.put(f.getName(), 0l);
        }

        lastStreaksUpdate = LocalDate.now();
    }

    public HabitsConfig getHabitsConfigDTO() throws IllegalAccessException {
        HabitsConfig hc = new HabitsConfig(0d, 0d, 0, 0, 0, (short) 0, (short) 0, false);
        for(Field f : HabitsConfig.class.getDeclaredFields()) {
            if(hbConfig.containsKey(f.getName())) {
                f.setAccessible(true);
                f.set(hc, hbConfig.get(f.getName()));
            }
        }
        return hc;
    }

    public StreaksData getStreaksData() {

        List<String> defStreaks = new ArrayList<>(List.of(
                "sleep", "wakeUpTime", "workedOut", "focus", "water",
                "reading", "studying", "meals", "detox",
                "planning"
        ));


        StreakWidgetProps[] props = new StreakWidgetProps[streaks.size()];
        if(!Objects.equals(lastStreaksUpdate, LocalDate.now())) {
            for(Streak s : streaks) {
                defStreaks.remove(s.name);
                props[streaks.indexOf(s)] = new StreakWidgetProps(s.getName(), s.getCount(), false);
            }
            lastStreaksUpdate = LocalDate.now();
        }else{
            for(Streak s : streaks) {
                defStreaks.remove(s.name);
                props[streaks.indexOf(s)] = new StreakWidgetProps(s.getName(), s.getCount(), s.doneToday);
            }
        }

        StreakWidgetProps[] props2 = new StreakWidgetProps[defStreaks.size()];
        for(String s : defStreaks) {
            props2[defStreaks.indexOf(s)] =  new StreakWidgetProps(s, (short)0, false);
        }
        return new StreaksData(concatenate(props, props2));
    }

    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    public DailyLogData getLogByDate(LocalDate arg0) {
        for(DailyLogData l : getLogs()) {
            if(l.getDate().isEqual(arg0)) {
                return l;
            }
        }
        DailyLogData d = new DailyLogData();
        replaceLogData(d);
        return d;
    }

    public DailyLogData getLogAndRemoveByDate(LocalDate arg0) {
        List<DailyLogData> list = new ArrayList<>(List.copyOf(getLogs()));
        DailyLogData dailyLogData = null;
        for(DailyLogData l : getLogs()) {
            if(l.getDate().equals(arg0)) {
                list.remove(l);
                dailyLogData = l;
            }
        }
        setLogs(list);
        return dailyLogData;
    }


    @Deprecated
    public void replaceLogData(DailyLogData data) {
        List<DailyLogData> list = new ArrayList<>(List.copyOf(getLogs()));
        DailyLogData toRemove = null;
        for(DailyLogData l : list) {
            if(l.getDate().equals(data.getDate())) {
                toRemove = l;
                break;
            }
        }
        if(toRemove != null) {
            list.remove(toRemove);
        }
        list.add(data);
        setLogs(list);
    }

    public void addLogData(DailyLogData data) {
        getLogs().add(data);
    }

}
