package com.daviipkp.lifedashboard.latest.services;

import com.daviipkp.lifedashboard.latest.dto.api.*;
import com.daviipkp.lifedashboard.latest.instance.*;
import com.daviipkp.lifedashboard.latest.repositories.ContentRep;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ContentRep contentRepository;
    private final UserRep userRepository;
    private final LeetCodeService leetCodeService;
    private final DuolingoService duolingoService;

    public DashboardService(ContentRep contentRepository, UserRep userRepository,
                            LeetCodeService leetCodeService, DuolingoService duolingoService) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.leetCodeService = leetCodeService;
        this.duolingoService = duolingoService;
    }

    @Transactional
    public UserContentData getOrCreateContent(UserAuthData user) {
        return contentRepository.findById(user.getContentID())
                .orElseGet(() -> {
                    UserContentData newData = new UserContentData();
                    newData = contentRepository.save(newData);
                    user.setContentID(newData.getID());
                    userRepository.save(user);
                    return newData;
                });
    }

    public StreaksData getStreakData(UserAuthData user) {
        StreakWidgetProps[] s = getOrCreateContent(user).getStreaksData().streaks();
        contentRepository.save(getOrCreateContent(user));
        StreakWidgetProps[] t = Arrays.copyOf(s, s.length + 2, StreakWidgetProps[].class);
        t[s.length] = new StreakWidgetProps("leetCodeSolved", (short)((int)leetCodeService.getStreakCount(user.getUsername())), false);
        t[s.length + 1] = new StreakWidgetProps("duoSolved", (short)((int)duolingoService.getStreakCount(user.getUsername())), duolingoService.getUserStats(user.getUsername()).practicedToday());
        return new StreaksData(t);
    }

    @Transactional
    public void savePlanning(UserAuthData user, String planningText) {
        UserContentData data = getOrCreateContent(user);
        DailyLogData log = data.getLogAndRemoveByDate(LocalDate.now());

        if (log == null) log = new DailyLogData();

        List<Streak> newS = new ArrayList<>(List.copyOf(data.getStreaks()));

        for(Streak s : data.getStreaks()) {
            if(s.getName().equalsIgnoreCase("planning")) {
                if(!s.isDoneToday()) {
                    if(planningText.length()>100) {
                        newS.remove(s);
                        newS.add(new Streak("planning", (short) (s.getCount() + 1), true));
                    }
                }else if(planningText.length()<100) {
                    newS.remove(s);
                    newS.add(new Streak("planning", (short) (s.getCount() - 1), false));
                }
            }
        }

        data.setStreaks(newS);

        log.setPlanning(planningText);
        data.addLogData(log);
        contentRepository.save(data);
    }

    @Transactional
    public StreaksData saveDailyLog(UserAuthData user, DailyLog logDto) {
        UserContentData data = getOrCreateContent(user);

        DuolingoService.DuolingoStats duoStats = duolingoService.getUserStats(user.getUsername());
        int lcCount = leetCodeService.getStreakCount(user.getUsername());

        DailyLogData yesterdayData = data.getLogByDate(LocalDate.now().minusDays(1));
        DailyLog yesterdayDto = (yesterdayData != null) ? convertToDto(yesterdayData) : getEmptyDailyLog();

        StreakWidgetProps[] currentStreaks = data.getStreaksData().streaks();
        StreakWidgetProps[] updatedStreaks = calculateUpdatedStreaks(
                currentStreaks,
                logDto,
                yesterdayDto,
                duoStats,
                lcCount
        ); List<Streak> streakEntities = Arrays.stream(updatedStreaks)
                .map(p -> new Streak(p.type(), p.count(), p.completedToday()))
                .collect(Collectors.toList());
        data.setStreaks(streakEntities);


        DailyLogData todayLog = data.getLogAndRemoveByDate(LocalDate.now());
        if (todayLog == null) todayLog = new DailyLogData();

        updateLogEntity(todayLog, logDto);
        data.addLogData(todayLog);

        contentRepository.save(data);
        return data.getStreaksData();
    }

    private StreakWidgetProps[] calculateUpdatedStreaks(StreakWidgetProps[] streaks,
                                                        DailyLog todayLog,
                                                        DailyLog yesterdayLog,
                                                        DuolingoService.DuolingoStats duoStats,
                                                        int lcCount) {

        return Arrays.stream(streaks).map(streak -> {
            String type = streak.type() != null ? streak.type().toLowerCase() : "";

            if (type.equals("duolingo")) {
                return new StreakWidgetProps(streak.type(), (short) duoStats.streak(), duoStats.practicedToday());
            }

            if (type.equals("leetcode")) {
                boolean solvedToday = lcCount > 0;
                return new StreakWidgetProps(streak.type(), (short) lcCount, solvedToday);
            }


            boolean isCompletedToday = checkManualCompletion(type, todayLog);
            boolean isCompletedYesterday = checkManualCompletion(type, yesterdayLog);


            short currentCount = streak.count();
            if (streak.completedToday()) {
                currentCount = (short) Math.max(0, currentCount - 1);
            }

            if (!isCompletedYesterday && currentCount > 0) {
                currentCount = 0;
            }

            short newCount = currentCount;

            if (isCompletedToday) {
                newCount++;
            }

            return new StreakWidgetProps(streak.type(), newCount, isCompletedToday);
        }).toArray(StreakWidgetProps[]::new);
    }

    public DailyData getDailyDataFormatted(UserAuthData user) {
        UserContentData data = getOrCreateContent(user);
        DailyLogData today = data.getLogByDate(LocalDate.now());

        String planning = (today != null) ? today.getPlanning() : "";
        DailyLog logDto = getLogDto(user);
        return new DailyData(planning, "Keep pushing!", logDto);
    }

    public DailyLog getLogDto(UserAuthData user) {
        UserContentData data = getOrCreateContent(user);

        return data.getLogs().stream()
                .filter(l -> Objects.equals(l.getDate(), LocalDate.now()))
                .findFirst()
                .map(this::convertToDto)
                .orElseGet(this::getEmptyDailyLog);
    }

    public PertinentData getPertinentData(UserAuthData user) {
        UserContentData data = getOrCreateContent(user);
        short[] days = new short[31];
        for(DailyLogData dl : data.getLogs()) {
            if(dl.getDate().getMonthValue() == LocalDate.now().getMonthValue()) {
                days[dl.getDate().getDayOfMonth()] = dl.getDayValue();
            }
        }
        CalendarData calendar = new CalendarData((short) LocalDate.now().getMonthValue(),
                (short) LocalDate.now().getYear(),
                days);
        return new PertinentData(data.getGoals().toArray(new Goal[data.getGoals().size()]), calendar);
    }

    private boolean checkManualCompletion(String type, DailyLog log) {
        if (type == null) return false;
        return switch (type.toLowerCase()) {
            case "workout", "workedout" -> Boolean.TRUE.equals(log.workedOut());
            case "detox" -> Boolean.TRUE.equals(log.detox());
            case "meals" -> log.meals() != null && log.meals() > 0;
            case "water", "waterintake" -> log.waterIntake() != null && log.waterIntake() > 0;
            case "reading" -> log.reading() != null && log.reading() > 0;
            case "studying" -> log.studying() != null && log.studying() > 0;
            case "focus", "focuslevel" -> log.focusLevel() != null && log.focusLevel() > 0;
            case "wakeup", "wakeuptime" -> log.wakeUpTime() != null && log.wakeUpTime() > 0;
            case "sleep", "sleeptime" -> log.sleepTime() != null && log.sleepTime() > 0;
            case "working" -> log.working() != null && log.working() > 0;
            default -> false;
        };
    }

    private void updateLogEntity(DailyLogData entity, DailyLog dto) {
        entity.setDetox(dto.detox());
        entity.setMeals(dto.meals());
        entity.setReading(dto.reading());
        entity.setStudying(dto.studying());
        entity.setSleepTime(dto.sleepTime());
        entity.setFocusLevel(dto.focusLevel());
        entity.setWorkedOut(dto.workedOut());
        entity.setWakeUpTime(dto.wakeUpTime());
        entity.setWaterIntake(dto.waterIntake());
        entity.setWorking(dto.working());
    }

    private DailyLog convertToDto(DailyLogData entity) {
        return new DailyLog(entity.getWakeUpTime(), entity.getSleepTime(), entity.isWorkedOut(),
                entity.getMeals(), entity.getWaterIntake(), entity.isDetox(), entity.getReading(),
                entity.getStudying(), entity.getFocusLevel(), entity.getWorking());
    }

    private DailyLog getEmptyDailyLog() {
        return new DailyLog(10.0d, 0d, false, 0, 0, false, 0, 0, (short) 0, 0);
    }
}