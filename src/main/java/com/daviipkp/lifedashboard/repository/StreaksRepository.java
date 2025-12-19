package com.daviipkp.lifedashboard.repository;

import com.daviipkp.lifedashboard.dto.Streaks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreaksRepository extends JpaRepository<Streaks, Long> {
}
