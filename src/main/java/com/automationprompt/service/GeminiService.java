package com.automationprompt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class GeminiService {
    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public GeminiService() {
        this.restTemplate = new RestTemplate();
    }

    public String getAiResponse(String prompt) {
        logger.debug("Preparing request for Gemini API with prompt: {}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> message = new HashMap<>();
        message.put("parts", Collections.singletonList(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", Collections.singletonList(message));
        body.put("safetySettings", Collections.emptyList());
        body.put("generationConfig", Map.of(
            "temperature", 0.7,
            "topK", 40,
            "topP", 0.95,
            "maxOutputTokens", 2048
        ));

        String fullUrl = UriComponentsBuilder.fromHttpUrl(apiUrl)
            .queryParam("key", apiKey)
            .toUriString();

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            logger.info("Sending request to Gemini API");
            ResponseEntity<Map> response = restTemplate.postForEntity(fullUrl, entity, Map.class);
            logger.debug("Received response from Gemini API: {}", response.getStatusCode());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                logger.debug("Response body: {}", responseBody);

                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> firstCandidate = candidates.get(0);
                    Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                    if (content != null) {
                        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                        if (parts != null && !parts.isEmpty()) {
                            String result = (String) parts.get(0).get("text");
                            logger.info("Successfully generated AI response");
                            return result;
                        }
                    }
                }
                logger.error("Unexpected response format from Gemini API: {}", responseBody);
                return "Error: Unexpected response format";
            }
            logger.error("Non-OK response from Gemini API: {}", response.getStatusCode());
            return "Error: API returned status " + response.getStatusCode();
        } catch (Exception e) {
            logger.error("Error calling Gemini API", e);
            return "Error: " + e.getMessage();
        }
    }
}
