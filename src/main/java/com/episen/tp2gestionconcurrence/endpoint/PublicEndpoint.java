package com.episen.tp2gestionconcurrence.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PublicEndpoint {

    @GetMapping
    public ResponseEntity<String> hello(Authentication authentication) {
        return ResponseEntity.ok("It's public");
    }
}
