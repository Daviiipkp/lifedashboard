package com.daviipkp.lifedashboard.repository;

import com.daviipkp.lifedashboard.dto.DailyData;
import com.daviipkp.lifedashboard.dto.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ConfigRepository  extends JpaRepository<UserConfig, Long> {}



