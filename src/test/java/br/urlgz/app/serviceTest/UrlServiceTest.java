package br.urlgz.app.serviceTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.urlgz.app.builderTest.UrlBuilder;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.mapper.UrlMapperInterface;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.repository.UrlRepository;
import br.urlgz.app.service.UrlService;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

  @InjectMocks
  private UrlService urlService;
  @Mock
  UrlRepository urlRepository;
  @Mock
  UrlMapperInterface urlMapperiInterface;

  UrlEntity urlEntity;
  UrlResponse urlResponse;
  UrlRequest urlRequest;
  UrlBuilder urlBuilder;

  @BeforeEach
  void setup() {
    this.urlBuilder = UrlBuilder.oneUrl();
    this.urlEntity = UrlBuilder.oneUrl().createUrlEntity();
    this.urlRequest = UrlBuilder.oneUrl().createUrlRequest();
    this.urlResponse = UrlBuilder.oneUrl().createUrlResponse();
  }

  @Test()
  void ShouldTransformALongUrlToAShortUrl() {
    when(urlMapperiInterface.toEntity(urlRequest)).thenReturn(urlEntity);
    when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);
    when(urlMapperiInterface.responseToDto(urlEntity)).thenReturn(urlBuilder.createUrlResponse());

    UrlResponse result = urlService.urlShortEncode(urlRequest);

    assertNotNull(result);
    assertTrue(result.shortUrl().startsWith("https://localhost:8080/"));
    assertEquals(result.shortCode(), this.urlResponse.shortCode());
    assertEquals(result.originalUrl(), this.urlRequest.url());
    assertEquals(result.createdAt().truncatedTo(ChronoUnit.SECONDS),
            this.urlEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
    assertEquals(result.totalClicks(), this.urlEntity.getClickCount());
    verify(urlMapperiInterface, times(1)).toEntity(urlRequest);
    verify(urlRepository, times(1)).save(urlEntity);
    verify(urlMapperiInterface, times(1)).responseToDto(urlEntity);  }

  @Test
  void ShouldReturnTheUrlFromCorrespondingCode() {
    when(urlRepository.findByShortCode(this.urlEntity.getShortCode())).thenReturn(this.urlEntity);
    when(urlMapperiInterface.responseToDto(this.urlEntity)).thenReturn(this.urlResponse);

    UrlResponse result = urlService.searchOriginalUrl(this.urlEntity.getShortCode());

    assertNotNull(result);
    assertEquals(this.urlEntity.getOriginalUrl(), result.originalUrl());

    assertEquals(result.createdAt().truncatedTo(ChronoUnit.SECONDS),
        this.urlEntity.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));

    assertEquals(this.urlEntity.getClickCount(), result.totalClicks());
    verify(urlRepository, times(1)).findByShortCode(this.urlEntity.getShortCode());
    verify(urlMapperiInterface, times(1)).responseToDto(urlEntity);

  }

  @Test
  void ShouldDeleteAnShortUrlEntity() {
    Long id = this.urlEntity.getId();

    when(urlRepository.existsById(id)).thenReturn(true);
    doNothing().when(urlRepository).deleteById(id);

    assertDoesNotThrow(() -> {
      urlService.deleteShortlUrlData(id);
    });

    verify(urlRepository, times(1)).existsById(id);
    verify(urlRepository, times(1)).deleteById(id);
  }

  @Test
  void ShouldThrowExceptionWhenShortCodeDoesNotExist() {

    when(urlRepository.findByShortCode("invalid")).thenReturn(null);

    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
      urlService.searchOriginalUrl("invalid");
    });

    assertEquals("Não foi possivel consultar a url curta.", ex.getMessage());
    verify(urlMapperiInterface, times(0)).responseToDto(any());
  }

  @Test
  void ShouldThrowExceptionWhenSavingFails(){
    when(urlMapperiInterface.toEntity(urlRequest)).thenThrow(new RuntimeException("Mapping error"));

    RuntimeException ex = assertThrows(RuntimeException.class, () -> {
      urlService.urlShortEncode(urlRequest);
    });

    assertEquals("Não foi possivel salvar a url.", ex.getMessage());
    verify(urlRepository, times(0)).save(any());
  }

  @Test
  void ShouldThrowExceptionWhenIdDoesNotExist() {
    Long id = this.urlEntity.getId();

    when(urlRepository.existsById(id)).thenReturn(false);

    Exception exception = assertThrows(RuntimeException.class, () -> {
      urlService.deleteShortlUrlData(id);
    });
    assertEquals("URL com ID " + id + " não encontrada.", exception.getMessage());
    verify(urlRepository, times(0)).deleteById(id);
  }
}
