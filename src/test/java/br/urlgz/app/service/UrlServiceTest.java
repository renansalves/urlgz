package br.urlgz.app.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.repository.UrlRepository;
import br.urlgz.app.mapper.UrlMapperInterface;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.builder.UrlBuilder;

  // Testes da service, função salvar, consultar, deletar. Função hash para
  // criação da url encurtada e unica.
  // 1 - Teste função Hash
  // 2 - Teste função Salvar
  // 3 - Teste função Consultar
  // 4 - Teste função Deletar.

  // shortcode -> 5 caracteres 62^5 -> 916.132.832 URLS distintas
  // Função hash, hasids
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
  void setup(){
    urlBuilder = new UrlBuilder();
    this.urlEntity = this.urlBuilder.createUrlEntity();
    this.urlRequest = this.urlBuilder.createUrlRequest();
    this.urlResponse = this.urlBuilder.createUrlResponse();
  }
  @Test
  void DeveTransformarUrlLongaEmCurta() {
        when(urlMapperiInterface.toEntity(urlRequest)).thenReturn(urlEntity); 
        when(urlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);
        when(urlMapperiInterface.responseToDto(urlEntity)).thenReturn(urlBuilder.createUrlResponse()); 

        UrlResponse resultado = urlService.urlShortEncode(urlRequest); 

        assertNotNull(resultado);
        verify(urlMapperiInterface, times(1)).toEntity(urlRequest);
        verify(urlRepository, times(1)).save(urlEntity); 
        verify(urlMapperiInterface, times(1)).responseToDto(urlEntity);

  }
}
