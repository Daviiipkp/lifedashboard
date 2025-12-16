package com.daviipkp.lifedashboard.repository;

import com.daviipkp.lifedashboard.dto.DailyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DailyDataRepository extends JpaRepository<DailyData, Long> {

    Optional<DailyData> findByDateBetween(Date inicio, Date fim);

}