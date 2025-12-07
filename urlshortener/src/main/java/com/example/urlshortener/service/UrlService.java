package com.example.urlshortener.service;

import com.example.urlshortener.entity.UrlEntity;
import com.example.urlshortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Slf4j
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final SecureRandom random = new SecureRandom();
    private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Value("${shortener.code-length}")
    private int codeLength;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(codeLength);

        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        return sb.toString();
    }

    public String shortenUrl(String originalUrl) {
        String code;

        do {
            code = generateShortCode();
        } while (urlRepository.findByShortCode(code).isPresent());

        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(code);

        urlRepository.save(entity);
        log.info("Created short URL {} -> {}", code, originalUrl);
        return code;
    }

    public Optional<String> getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(UrlEntity::getOriginalUrl);
    }
}
