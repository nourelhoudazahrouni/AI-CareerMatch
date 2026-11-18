
package com.careermatch.controller;

import com.careermatch.dto.MatchRequest;
import com.careermatch.dto.MatchResult;
import com.careermatch.repository.InternshipRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final InternshipRepository internshipRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.service.url:http://localhost:8000}")
    private String aiServiceUrl;

    public RecommendationController(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    @GetMapping
    public ResponseEntity<List<MatchResult>> recommend(
            @RequestParam String skills) {

        MatchRequest request = new MatchRequest(
            skills,
            internshipRepository.findAll()
        );

        ResponseEntity<List<MatchResult>> response =
            restTemplate.exchange(
                aiServiceUrl + "/match",
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<List<MatchResult>>() {}
            );

        return ResponseEntity.ok(response.getBody());
    }
}
