package com.daviipkp.lifedashboard.latest.services;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

@Service
public class InsightService {

    public String getInsight(String userAPIKey, String provider, String pertinentData) throws ExecutionException, InterruptedException {
        String url = "https://ai.hackclub.com/proxy/v1/chat/completions";

        String jsonBody = String.format("""
            {
                "model": "qwen/qwen3-32b",
                "messages": [{"role": "user", "content": "Generate a productive and meaningful insight for the following user data: %s"}]
            }
            """, pertinentData);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + userAPIKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();


        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).get().toString();
    }

}
