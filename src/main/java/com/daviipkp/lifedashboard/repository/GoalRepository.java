package com.daviipkp.lifedashboard.repository;

import com.daviipkp.lifedashboard.dto.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByDate(LocalDate date);
}
