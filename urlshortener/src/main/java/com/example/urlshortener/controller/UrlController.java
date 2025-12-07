package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    @PostMapping("/shorten")
    @ResponseBody
    public ResponseEntity<?> shorten(@RequestBody UrlRequest request) {

        if (request == null || request.url() == null || request.url().isEmpty()) {
            return ResponseEntity.badRequest().body("URL must not be empty");
        }

        String shortCode = urlService.shortenUrl(request.url());
        log.info("Generated short code '{}' for URL '{}'", shortCode, request.url());
        return ResponseEntity.ok(new UrlResponse(shortCode));
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {

        return urlService.getOriginalUrl(shortCode)
                .map(url -> {
                    log.info("Redirecting short code '{}' -> '{}'", shortCode, url);
                    return ResponseEntity.status(302).location(URI.create(url)).build();
                })
                .orElseGet(() -> {
                    log.warn("Short code '{}' not found for redirect", shortCode);
                    return ResponseEntity.notFound().build();
                });
    }


    public record UrlRequest(String url) {}
    public record UrlResponse(String shortCode) {}
}
