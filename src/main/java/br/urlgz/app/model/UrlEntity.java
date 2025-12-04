package br.urlgz.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class UrlEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false)
 private LocalDateTime createdAt;

 @Column(nullable = false)
 private LocalDateTime expiresAt;

 @Column(nullable = false, unique = true)
 private String shortCode;

 @Column(nullable = false)
 private String originalUrl;
 @Column(nullable = false)
 private int clickCount = 0;
 @Column(nullable=false)
 private Boolean isActive = true;

 public UrlEntity(Long id, String shortCode, String originalUrl, int clickCount, Boolean isActive) {
	this.id = id;
	this.shortCode = shortCode;
	this.originalUrl = originalUrl;
	this.clickCount = clickCount;
	this.isActive = isActive;
  this.createdAt = LocalDateTime.now();
  this.expiresAt = LocalDateTime.now().plusDays(30);
 }


}
