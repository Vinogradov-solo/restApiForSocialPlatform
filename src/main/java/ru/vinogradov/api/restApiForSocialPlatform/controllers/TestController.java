package ru.vinogradov.api.restApiForSocialPlatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> testTokenValidation() {
        return ResponseEntity.ok().build();
    }
}
