package com.daviipkp.lifedashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeetCodeService {

    public Integer getSolvedCount() {
        try {
            String url = "https://leetcode.com/graphql";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String username = "daviipkp";

            // 1. Cria os Cabeçalhos para fingir ser um navegador (Isso resolve o erro 403)
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Referer", "https://leetcode.com/"); // O segredo está aqui
            headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");

            // 2. Monta o Corpo da Requisição
            String query = String.format("{ \"query\": \"{ matchedUser(username: \\\"%s\\\") { submitStats: submitStatsGlobal { acSubmissionNum { difficulty count } } } }\" }", username);

            // 3. Empacota tudo (Headers + Query)
            HttpEntity<String> entity = new HttpEntity<>(query, headers);

            // 4. Dispara a requisição
            String jsonResponse = restTemplate.postForObject(url, entity, String.class);

            // 5. O Parser (igual ao anterior)
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
            System.out.println("Erro ao buscar LeetCode: " + e.getMessage());
            return -1;
        }
    }
}