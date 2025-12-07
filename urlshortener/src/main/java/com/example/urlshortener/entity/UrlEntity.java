package com.example.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Setter
    @Column(name = "short_code", nullable = false, unique = true)
    private String shortCode;

    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UrlEntity() {} // обязательно нужен пустой конструктор

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
