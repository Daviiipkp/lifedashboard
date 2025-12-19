package com.daviipkp.lifedashboard.repository;

import com.daviipkp.lifedashboard.dto.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PlanningRepository extends JpaRepository<Planning, LocalDate> {}