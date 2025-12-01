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

 @Column(name="created_at", nullable = false)
 private LocalDateTime createdAt;

 @Column(name="expires_at", nullable = false)
 private LocalDateTime expiresAt;

 @Column(name = "short_code",unique = true, nullable = false)
 private String shortCode;

 @Column(name="original_url", nullable = false)
 private String originalUrl;
 @Column(name="click_count", columnDefinition="Integer default '0'")
 private int clickCount;
 @Column(name="is_active", nullable=false)
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
