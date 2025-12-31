package com.daviipkp.lifedashboard.latest.controllers;

import com.daviipkp.lifedashboard.latest.dto.api.StreaksData;
import com.daviipkp.lifedashboard.latest.instance.UserAuthData;
import com.daviipkp.lifedashboard.latest.instance.UserContentData;
import com.daviipkp.lifedashboard.latest.repositories.ContentRep;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private ContentRep contentRepository;

    @Autowired
    private UserRep userRepository;

    @GetMapping("/streaksdata")
    public StreaksData getStreakData(@AuthenticationPrincipal UserAuthData user, HttpServletRequest request){
        if(user == null || !user.isEnabled()) {
            return null;
        }
        UserContentData data = contentRepository.findByID(user.getContentID()).orElse(null);
        if(data == null) {
            data = new UserContentData();
            contentRepository.save(data);
            user.setContentID(data.getID());
            userRepository.save(user);
        }
        System.out.println("passed getStreaksData as " + data.getStreaksData());
        return data.getStreaksData();
    }


}
