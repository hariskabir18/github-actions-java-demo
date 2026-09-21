package com.demo.githubactions.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${demo.version:local}")
    private String version;

    @Value("${demo.commit:unknown}")
    private String commit;

    @Value("${demo.environment:DEV}")
    private String environment;

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {
        return String.join(System.lineSeparator(),
                "============================================================",
                "           GITHUB ACTIONS CI/CD DEMO - Test",
                "============================================================",
                " Application       : Java Spring Boot Demo",
                " Environment       : " + environment,
                " Version           : " + version,
                " Commit            : " + commit,
                " Status            : RUNNING",
                " HTTP Port         : 8080",
                "------------------------------------------------------------",
                " Message           : Deployment is healthy and reachable.",
                "============================================================",
                "");
    }

    @GetMapping("/api/hello")
    public Map<String, Object> hello() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("application", "GitHub Actions Java Demo");
        response.put("environment", environment);
        response.put("version", version);
        response.put("commit", commit);
        response.put("status", "RUNNING");
        response.put("timestamp", Instant.now().toString());
        return response;
    }
}
