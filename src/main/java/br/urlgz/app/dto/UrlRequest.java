package br.urlgz.app.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;

public record UrlRequest(

  @NotBlank
  String url,
  @DateTimeFormat()
  LocalTime expiresIn
    ) {}
