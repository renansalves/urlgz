package br.urlgz.app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import br.urlgz.app.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.dto.UrlResponse;

@RestController
@RequestMapping("/")
@Tag(name = "Url", description = "Serviço responsavel por gerenciar e armazenar URL's curtas e suas respectivas urls longas.")
public class UrlController {

  @Autowired
  UrlService urlService;

  @Operation(
  summary = "Cria uma nova URL curta",
  description = "Cria uma URL curta e armazena no banco de dados, com a sua respectiva URL longa.", 
  responses = {
      @ApiResponse(responseCode = "201", 
      description = "Url encurtada com sucesso", content = @Content(schema = @Schema(implementation = UrlResponse.class)))
  })
  @PostMapping("/")
  public ResponseEntity<UrlResponse> saveUrl(@RequestBody UrlRequest urlRequest) {
    UrlResponse urlResponse = urlService.urlShortEncode(urlRequest);

    URI resource = ServletUriComponentsBuilder.fromCurrentRequest()
      .path("/{shortCode}")
      .buildAndExpand(urlResponse.shortCode())
      .toUri();

    return ResponseEntity.created(resource).body(urlResponse);

  }

  @Operation(summary = "Consulta uma URL curta, e redireciona para a url longa.",
  description = "Redireciona a URL curta, para sua respectiva URL longa que foi armazenada no banco de dados.",
  responses = {
      @ApiResponse(
      responseCode = "201",
      description = "Pessoa criada com sucesso",
      content = @Content(schema = @Schema(implementation = UrlResponse.class)
        )),
  })
  @GetMapping("/")
  public ResponseEntity<UrlResponse> getUrl(@RequestBody String shortCode) {
    return null;
  }

  @Operation(
  summary = "Remove um resgistro de URL curta.", description = "Remove o registro da url curta, que se encontra armazeda no banco de dados.",
  responses = {
      @ApiResponse(
      responseCode = "201",
      description = "Pessoa criada com sucesso",
      content = @Content(schema = @Schema(implementation = UrlResponse.class)
        )),
  })
  @DeleteMapping("/")
  public ResponseEntity<UrlResponse> deleteUrl(@RequestBody Long id) {

    return null;

  }
}
