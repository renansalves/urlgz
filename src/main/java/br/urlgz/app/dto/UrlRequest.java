package br.urlgz.app.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;

public record UrlRequest(

  @NotBlank
  String url,
  @DateTimeFormat()
  LocalDateTime expiresIn
    ) {}
