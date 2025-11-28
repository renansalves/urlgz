package br.urlgz.app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.urlgz.app.dto.*;

public class UrlServiceTest {

  @Autowired
  private UrlResponse urlResponse;
  @Autowired
  private UrlService urlService;
// Testes da service, função salvar, consultar, deletar. Função hash para criação da url encurtada e unica.
// 1 - Teste função Hash
// 2 - Teste função Salvar
// 3 - Teste função Consultar
// 4 - Teste função Deletar.


// shortcode -> 5 caracteres 62^5 -> 916.132.832 URLS distintas
// Função hash, hasids
  
  @Test
  void DeveTransformarUrlLongaEmCurta(){
    final String url = "www.google.com";
    
    // deve retornar o objeto 
    urlResponse = urlService.urlShortEncode(url);
    
  }
}
