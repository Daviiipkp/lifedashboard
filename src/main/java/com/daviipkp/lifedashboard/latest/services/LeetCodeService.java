package com.daviipkp.lifedashboard.latest.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeetCodeService {

    public Integer getStreakCount(String username) {
        try {
            String url = "https://leetcode.com/graphql";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();


            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Referer", "https://leetcode.com/"); // O segredo está aqui
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            String query = String.format("{ \"query\": \"{ matchedUser(username: \\\"%s\\\") { submitStats: submitStatsGlobal { acSubmissionNum { difficulty count } } } }\" }", username);

            HttpEntity<String> entity = new HttpEntity<>(query, headers);

            String jsonResponse = restTemplate.postForObject(url, entity, String.class);

            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode submissions = root.path("data")
                    .path("matchedUser")
                    .path("submitStats")
                    .path("acSubmissionNum");

            if (submissions.isArray()) {
                for (JsonNode node : submissions) {
                    if ("All".equals(node.get("difficulty").asText())) {
                        return node.get("count").asInt();
                    }
                }
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}