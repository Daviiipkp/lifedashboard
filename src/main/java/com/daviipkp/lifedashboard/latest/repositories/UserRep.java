package com.daviipkp.lifedashboard.latest.repositories;

import com.daviipkp.lifedashboard.latest.instance.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRep extends JpaRepository<UserData, Long> {
    Optional<UserData> findByEmail(String email);
    Optional<UserData> findByUsername(String username);
}
