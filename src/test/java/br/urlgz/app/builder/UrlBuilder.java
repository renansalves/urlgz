package br.urlgz.app.builder;

import java.time.LocalDateTime;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.*;

public class UrlBuilder {

    private Long id = 101010L;
    private String url = "Joao Pedro";
    private LocalDateTime expiredAt = LocalDateTime.now().plusDays(30);
    private LocalDateTime createdAt = LocalDateTime.now();
    private String shortCode = "qhc";
    private Boolean isActive = true;
    private int totalClicks = 0;


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
        return new UrlResponse(this.shortCode,this.url, this.createdAt,this.totalClicks);

    }
}
