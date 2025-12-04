package br.urlgz.app.handler;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ApiError {

  private int status;
  private String message;
  private LocalDateTime tiemstamp;
  private String details; 

  public ApiError(String message, int status, LocalDateTime tiemstamp, String details) {
    this.message = message;
    this.status = status;
    this.tiemstamp = tiemstamp;
    this.details = details;
  }


}
