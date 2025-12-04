
package br.urlgz.app.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.urlgz.app.builderTest.UrlBuilder;
import br.urlgz.app.controller.UrlController;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.service.UrlService;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {

  @MockBean
  private UrlService urlService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private UrlRequest urlRequest;
  private UrlResponse urlResponse;

  @BeforeEach
  void setup() {
    urlRequest = UrlBuilder.oneUrl().createUrlRequest();
    urlResponse = UrlBuilder.oneUrl().createUrlResponse();
  }

  @Test
  void shouldCreateShortCodeAndReturn201Created() throws Exception {
    when(urlService.urlShortEncode(any(UrlRequest.class))).thenReturn(urlResponse);

    mockMvc.perform(post("/url/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(urlRequest)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(header().string(HttpHeaders.LOCATION, org.hamcrest.Matchers.startsWith("http://localhost/url/")))
        .andExpect(jsonPath("$.shortCode").value(urlResponse.shortCode()))
        .andExpect(jsonPath("$.originalUrl").value(urlResponse.originalUrl()))
        .andExpect(jsonPath("$.shortUrl").value(urlResponse.shortUrl()))
        .andExpect(jsonPath("$.createdAt").value(org.hamcrest.Matchers.startsWith("2025-12-04")))
        .andExpect(jsonPath("$.totalClicks").value((int) urlResponse.totalClicks())); // jsonPath numeric as int

    verify(urlService).urlShortEncode(any(UrlRequest.class));
  }

  @Test
  void shouldReturnUrlFromShortCode() throws Exception {
    String code = urlResponse.shortCode();
    when(urlService.searchOriginalUrl(code)).thenReturn(urlResponse);

    mockMvc.perform(get("/url/{shortCode}", code)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.shortCode").value(code))
        .andExpect(jsonPath("$.originalUrl").value(urlResponse.originalUrl()))
        .andExpect(jsonPath("$.shortUrl").value(urlResponse.shortUrl()));

    verify(urlService).searchOriginalUrl(code);
  }

  @Test
  void shouldRedirectToOriginalUrl() throws Exception {
    String code = urlResponse.shortCode();
    when(urlService.searchOriginalUrl(code)).thenReturn(urlResponse);

    mockMvc.perform(get("/{shortcode}", code))
        .andDo(print())
        .andExpect(status().isFound())
        .andExpect(header().string(HttpHeaders.LOCATION, urlResponse.originalUrl()));

    verify(urlService).searchOriginalUrl(code);
  }

  @Test
  void deleteReturns204NoContent() throws Exception {
    Long id = 123L;
    // The controller delegates to service; just ensure no exception
    doNothing().when(urlService).deleteShortlUrlData(ArgumentMatchers.eq(id));

    mockMvc.perform(delete("/url/{id}", id))
        .andDo(print())
        .andExpect(status().isNoContent());

    verify(urlService).deleteShortlUrlData(id);
  }
}
