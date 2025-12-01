package br.urlgz.app.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public record UrlRequest(

  @NotBlank
  String url,
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Future
  LocalDateTime expiresAt
    ) {}
