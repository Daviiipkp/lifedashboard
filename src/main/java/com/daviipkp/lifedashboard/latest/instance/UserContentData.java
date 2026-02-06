package com.daviipkp.lifedashboard.latest.instance;

import com.daviipkp.lifedashboard.latest.dto.api.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    @JsonIgnore
    @Transient
    private List<StreakWidgetProps> defaultStreaks = new ArrayList<>();

    public UserContentData() {

        defaultStreaks.add(new StreakWidgetProps("Sleep",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Wake up Time",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Worked Out",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Focus",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Water Intake",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Reading",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Studying",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Meal count",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Detox",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));
        defaultStreaks.add(new StreakWidgetProps("Planning",
                (short)0,
                false,
                StreakColors.create("blue", "300", "500/60", "500", "lg", "950/50")
        ));

        for(int i = 0; i<defaultStreaks.size(); i++) {
            streaks.add(new Streak(defaultStreaks.get(i).type(), (short)0, false));
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
        StreakWidgetProps[] props = new StreakWidgetProps[defaultStreaks.size()];
        if(!Objects.equals(lastStreaksUpdate, LocalDate.now())) {

            for(StreakWidgetProps s : defaultStreaks) {
                Streak str = getStreakByName(s.type());
                streaks.remove(str);
                str.doneToday = false;
                streaks.add(str);
                props[defaultStreaks.indexOf(s)] = new StreakWidgetProps(s.type(), str.getCount(), false, s.currentTheme());
            }
            lastStreaksUpdate = LocalDate.now();
        }else{
            for(StreakWidgetProps s : defaultStreaks) {
                Streak str = getStreakByName(s.type());
                props[defaultStreaks.indexOf(s)] = new StreakWidgetProps(s.type(), str.getCount(), str.doneToday, s.currentTheme());
            }
        }
        return new StreaksData(props);
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

    public Streak getStreakByName(String arg0) {
        for(Streak s: streaks) {
            if(s.getName().equalsIgnoreCase(arg0)) {
                return s;
            }
        }
        return null;
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
