package br.urlgz.app.builder;

import java.time.LocalDateTime;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.*;

public class UrlBuilder {

    private static final String BASESHORTURL= "https://localhost:8080/";
  	private Long id = 101010L;
    private String url = "https://www.urlmuitolonga/esta/e/uma/url/longa";
    private LocalDateTime expiredAt = LocalDateTime.now().plusDays(30);
    private LocalDateTime createdAt = LocalDateTime.now();
    private String shortCode = "qhc";
    private Boolean isActive = true;
    private int totalClicks = 0;
    private String shortUrl = BASESHORTURL+shortCode;

    public static UrlBuilder oneUrl() {
        return new UrlBuilder();
    }

    // Métodos para customização
    public UrlBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UrlBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public UrlBuilder withUrlAtiva(String url) {
        this.url = url;
        return this;
    }

    public UrlBuilder withShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public UrlBuilder withClicks(int totalClicks){
      this.totalClicks = totalClicks;
      return this;
    }
    public UrlBuilder withoutClicks() {
        this.totalClicks = 0;
        return this;
    }

    public UrlEntity createUrlEntity() {
        return new UrlEntity(this.id, this.expiredAt, LocalDateTime.now().plusDays(30), this.shortCode, this.url, this.totalClicks, this.isActive);
    }

    public UrlRequest createUrlRequest() {
        return new UrlRequest(this.url, this.expiredAt);
    }

    public UrlResponse createUrlResponse() {
        return new UrlResponse(this.shortCode,this.url,this.shortUrl, this.createdAt,this.totalClicks);

    }
}
