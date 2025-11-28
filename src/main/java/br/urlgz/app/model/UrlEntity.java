package br.urlgz.app.model;

import java.time.LocalDateTime;
import java.util.Optional;

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
 @Column(name="is_active", columnDefinition="Boolean default 'true'")
 private Boolean isActive;
 public Long getId() {
	return id;
 }
 public void setId(Long id) {
	this.id = id;
 }
 public LocalDateTime getCreatedAt() {
	return createdAt;
 }
 public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
 }
 public LocalDateTime getExpiresAt() {
	return expiresAt;
 }
 public void setExpiresAt(LocalDateTime expiresAt) {
	this.expiresAt = expiresAt;
 }
 public String getShortCode() {
	return shortCode;
 }
 public void setShortCode(String shortCode) {
	this.shortCode = shortCode;
 }
 public String getOriginalUrl() {
	return originalUrl;
 }
 public void setOriginalUrl(String originalUrl) {
	this.originalUrl = originalUrl;
 }
 public int getClickCount() {
	return clickCount;
 }
 public void setClickCount(int clickCount) {
	this.clickCount = clickCount;
 }
 public Boolean getIsActive() {
	return isActive;
 }
 public void setIsActive(Boolean isActive) {
	this.isActive = isActive;
 }
 public UrlEntity(Long id, LocalDateTime createdAt, LocalDateTime expiresAt, String shortCode, String originalUrl,
		int clickCount, Boolean isActive) {
	this.id = id;
	this.createdAt = createdAt;
	this.expiresAt = expiresAt;
	this.shortCode = shortCode;
	this.originalUrl = originalUrl;
	this.clickCount = clickCount;
	this.isActive = isActive;
 }
 public UrlEntity() {

 }

}
