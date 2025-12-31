package com.daviipkp.lifedashboard.latest.repositories;

import com.daviipkp.lifedashboard.latest.instance.UserAuthData;
import com.daviipkp.lifedashboard.latest.instance.UserContentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ContentRep extends JpaRepository<UserContentData, Long> {
    Optional<UserContentData> findByID(long ID);
}
