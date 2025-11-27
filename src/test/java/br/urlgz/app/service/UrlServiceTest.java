package br.urlgz.app.service;

import org.junit.jupiter.api.Test;

public class UrlServiceTest {

// Testes da service, função salvar, consultar, deletar. Função hash para criação da url encurtada e unica.
// 1 - Teste função Hash
// 2 - Teste função Salvar
// 3 - Teste função Consultar
// 4 - Teste função Deletar.


// shortcode -> 5 caracteres 62^5 -> 916.132.832 URLS distintas
// Função hash, hasids
  @Test
  DeveTransformarUrlLongaEmCurta(){
    final String url = "www.google.com";
    UrlService.urlEncode(url);

  }
}
