package com.example.exampleappmicroservice.Services.ServicesImpl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenAiService {
    private final String API_KEY = "your_api_key";

    public String generateText(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]\n" +
                "}";


        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions", entity, String.class
        );
        return response.getBody();
    }
}
