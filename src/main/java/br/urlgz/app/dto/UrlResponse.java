package br.urlgz.app.dto;

import java.time.LocalDateTime;

public record UrlResponse(
    String shortCode,
    String originalUrl,
    String shortUrl,
    LocalDateTime createdAt,
    long totalClicks
    ) {
}
