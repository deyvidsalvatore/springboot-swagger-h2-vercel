package br.com.exemplo.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    @Operation(summary = "Verifica se a API está respondendo")
    public Map<String, String> health() {
        return Map.of("status", "UP", "app", "springboot-swagger-h2-vercel");
    }
}
