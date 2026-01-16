package com.daviipkp.lifedashboard.latest.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DuolingoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DuolingoService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public record DuolingoStats(int streak, boolean practicedToday) {}

    public DuolingoStats getUserStats(String username) {
        if (username == null || username.isBlank()) return new DuolingoStats(0, false);

        try {
            String url = "https://www.duolingo.com/2017-06-30/users?username=" + username;

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode usersArray = root.path("users");

                if (usersArray.isArray() && !usersArray.isEmpty()) {
                    JsonNode userProfile = usersArray.get(0);

                    int streak = userProfile.path("streak").asInt();
                    boolean practiced = userProfile.path("streak_extended_today").asBoolean();
                    return new DuolingoStats(streak, practiced);
                }
            }
        } catch (Exception e) {}
        return new DuolingoStats(0, false);
    }
    public int getStreakCount(String username) {
        return getUserStats(username).streak();
    }
}