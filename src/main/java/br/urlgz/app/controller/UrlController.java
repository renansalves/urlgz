package br.urlgz.app.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.urlgz.app.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import br.urlgz.app.handler.ErrorHandler;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.dto.UrlResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/")
@Tag(name = "Url", description = "Serviço responsavel por gerenciar e armazenar URL's curtas e suas respectivas urls longas.")
public class UrlController {
  private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
  @Autowired
  UrlService urlService;

  @Operation(summary = "Cria uma nova URL curta", description = "Cria uma URL curta e armazena no banco de dados, com a sua respectiva URL longa.", responses = {
      @ApiResponse(responseCode = "201", description = "Url encurtada com sucesso", content = @Content(schema = @Schema(implementation = UrlResponse.class)))
  })
  @PostMapping("url/")
  public ResponseEntity<UrlResponse> saveUrl(@Valid @RequestBody UrlRequest urlRequest) {
    logger.debug("Saving URL: {}", urlRequest);

    logger.debug("Saving URL: {}", urlRequest);
    UrlResponse urlResponse = urlService.urlShortEncode(urlRequest);

    URI resource = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{shortCode}")
        .buildAndExpand(urlResponse.shortCode())
        .toUri();

    logger.debug("URL saved successfully");
    return ResponseEntity.created(resource).body(urlResponse);
  }

  @Operation(summary = "Consulta uma URL curta, e redireciona para a url longa.", description = "Redireciona a URL curta, para sua respectiva URL longa que foi armazenada no banco de dados.", responses = {
      @ApiResponse(responseCode = "200", description = "Consulta da url retornada com sucesso.", content = @Content(schema = @Schema(implementation = UrlResponse.class))),
      @ApiResponse(responseCode = "404", description = "Url nao encontrada.")
  })
  @GetMapping("/url/{shortCode}")
  public ResponseEntity<UrlResponse> getUrl(@PathVariable String shortCode) {
    logger.debug("URL found: {}", shortCode);
    UrlResponse response = urlService.searchOriginalUrl(shortCode);

    logger.debug("URL found: {}", response);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Redireciona para a URL longa a partir do shortcode.", description = "Redireciona a URL longa a partir do shortcode fornecido.", responses = {
      @ApiResponse(responseCode = "302", description = "Redirecionamento para a URL longa."),
      @ApiResponse(responseCode = "404", description = "URL não encontrada.")
  })
  @GetMapping("/{shortcode}")
  public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortcode) {
    UrlResponse urlResponse = urlService.searchOriginalUrl(shortcode);
    if (urlResponse == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    URI originalUrl = URI.create(urlResponse.originalUrl());
    return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, originalUrl.toString()).build();
  }

  @Operation(summary = "Remove um resgistro de URL curta.", description = "Remove o registro da url curta, que se encontra armazeda no banco de dados.", responses = {
      @ApiResponse(responseCode = "204", description = "Url removida com sucesso", content = @Content(schema = @Schema(implementation = UrlResponse.class))),
  })
  @DeleteMapping("url/{id}")
  public ResponseEntity<Void> deleteUrl(@PathVariable Long id) {
    urlService.deleteShortlUrlData(id);
    return ResponseEntity.noContent().build();

  }
}
