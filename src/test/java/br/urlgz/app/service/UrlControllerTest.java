package br.urlgz.app.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.urlgz.app.controller.UrlController;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.builder.UrlBuilder;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {

  @MockBean
  private UrlService urlService;

  @InjectMocks
  private UrlController urlController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final String BASE_URL = "/";

  UrlRequest urlRequest;
  UrlResponse urlResponse;

  @BeforeEach
  void setup(){
   urlRequest = UrlBuilder.oneUrl().createUrlRequest();
   urlResponse = UrlBuilder.oneUrl().createUrlResponse();
  }
  @Test
  void shouldCreateShortCodeAndReturn201Created() throws Exception {

    when(urlService.urlShortEncode(eq(urlRequest))).thenReturn(urlResponse);

    mockMvc.perform(post(BASE_URL)
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(urlRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.shortCode").value(urlResponse.shortCode()))
        .andExpect(jsonPath("$.shortUrl").value(urlResponse.shortUrl()))
        .andExpect(jsonPath("$.originalUrl").value(urlResponse.originalUrl()))
        .andExpect(jsonPath("$.createdAt").value(urlResponse.createdAt().toString()))
        .andExpect(jsonPath("$.totalClicks").value(urlResponse.totalClicks()));

    verify(urlService, times(1)).urlShortEncode(eq(urlRequest));
  }

  @Test
  void shouldReturnUrlFromShortCode() throws Exception {
    
  String url = urlResponse.shortCode();
	when(urlService.searchOriginalUrl(eq(url))).thenReturn(urlResponse); 
      System.out.println("URL request: " + urlRequest);
    System.out.println("URL response: " + urlResponse);

}}
