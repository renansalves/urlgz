package br.urlgz.app.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.urlgz.app.dto.*;

public class UrlServiceTest {

  @Autowired
  private UrlService urlService;
  // Testes da service, função salvar, consultar, deletar. Função hash para
  // criação da url encurtada e unica.
  // 1 - Teste função Hash
  // 2 - Teste função Salvar
  // 3 - Teste função Consultar
  // 4 - Teste função Deletar.

  // shortcode -> 5 caracteres 62^5 -> 916.132.832 URLS distintas
  // Função hash, hasids

  @Test
  void DeveTransformarUrlLongaEmCurta() {
    final String url = "www.google.com";

    // deve retornar o objeto

    final UrlResponse urlResponse = urlService.urlShortEncode(url);
    assertNotNull(urlResponse);

  }
}
