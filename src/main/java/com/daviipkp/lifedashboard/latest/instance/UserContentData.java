package com.daviipkp.lifedashboard.latest.instance;

import com.daviipkp.lifedashboard.latest.dto.api.DailyData;
import com.daviipkp.lifedashboard.latest.dto.api.DailyLog;
import com.daviipkp.lifedashboard.latest.dto.api.StreakWidgetProps;
import com.daviipkp.lifedashboard.latest.dto.api.StreaksData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Array;
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
    List<DailyLogData> logs = new ArrayList<>();

    @ElementCollection
    private List<Streak> streaks = new ArrayList<>();

    public UserContentData() {
        List<String> defStreaks = List.of(
                "sleep", "wakeUpTime", "workedOut", "focus", "water",
                "reading", "studying", "meals", "detox",
                "planning", "leetCodeSolved", "duoSolved"
        );
        for(int i = 0; i<12; i++) {
            streaks.add(new Streak(defStreaks.get(i), (short)new Random().nextInt(0, 250), true));
        }

    }

    public StreaksData getStreaksData() {
        List<String> defStreaks = new ArrayList<>(List.of(
                "sleep", "wakeUpTime", "workedOut", "focus", "water",
                "reading", "studying", "meals", "detox",
                "planning", "leetCodeSolved", "duoSolved"
        ));
        StreakWidgetProps[] props = new StreakWidgetProps[streaks.size()];
        for(Streak s : streaks) {
            defStreaks.remove(s.name);
            props[streaks.indexOf(s)] = new StreakWidgetProps(s.getName(), s.getCount(), s.doneToday);
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

}
