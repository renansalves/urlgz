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
@Getter
@Setter
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

}
