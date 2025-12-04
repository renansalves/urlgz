package br.urlgz.app.integrationTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.repository.UrlRepository;
import br.urlgz.app.service.UrlService;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UrlIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  
  @Autowired 
  UrlRepository urlRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UrlService urlService;

  private final String BASE_URL = "/url";

  @Test
  void ShouldCreateAUrlAndReturn201Created() throws Exception {
    String url = """
        {
            "url": "https://www.google.com"
        }
        """;

    MvcResult result = mockMvc.perform(post(BASE_URL + "/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(url))
        .andExpect(status().isCreated())
        .andReturn();

    String body = result.getResponse().getContentAsString();
    JsonNode root = objectMapper.readTree(body);

    assertNotNull(root, "A resposta não deve conter um JSON nulo");
    assertTrue(root.has("shortCode"), "Deve retornar o 'shortCode' na resposta do JSON");
    assertTrue(root.has("shortUrl"), "Deve retornar a 'shortUrl' na resposta do JSON");
    assertTrue(root.has("originalUrl"), "Expected 'originalUrl'  na resposta do JSON");

    String shortCode = root.path("shortCode").asText(null);
    String shortUrl = root.path("shortUrl").asText(null);
    String originalUrl = root.path("originalUrl").asText(null);

    assertNotNull(shortCode, "shortCode Não pode ser nulo");
    assertNotNull(shortUrl, "shortUrl Não pode ser nulo");
    assertNotNull(originalUrl, "originalUrl Não pode ser nulo");

    assertTrue(shortUrl.startsWith("https://localhost:8080/"));
  }

  @Test
  void shouldReturnUrlFromShortCode() throws Exception {
    String url = """
        {
            "url": "https://www.google.com"
        }
        """;
    MvcResult result = mockMvc.perform(post(BASE_URL + "/")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .content(url))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String body = result.getResponse().getContentAsString();
    JsonNode root = objectMapper.readTree(body);
    
    String shortCode = root.path("shortCode").asText(null);



    mockMvc.perform(get("/url/{shortCode}", shortCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shortCode").value(shortCode))
        .andExpect(jsonPath("$.originalUrl").value("https://www.google.com"));
  }

  void ShouldRedirectAnShortUrlToALongUrlAndReturn302() throws Exception {
    String url = """
        {
            "url": "https://www.google.com"
        }
        """;
    MvcResult result = mockMvc.perform(post(BASE_URL + "/")
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .content(url))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String body = result.getResponse().getContentAsString();
    JsonNode root = objectMapper.readTree(body);
    
    String shortCode = root.path("shortCode").asText(null);



    mockMvc.perform(get("/url/{shortCode}", shortCode))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.shortCode").value(shortCode));
        
  }


 @Test
  void ShouldDeleteAnUrlFromDatabaseAndReturn204() throws Exception {
    UrlEntity e = new UrlEntity();
    e.setOriginalUrl("https://www.google.com");
    e.setShortCode("xyz987");
    e.setCreatedAt(LocalDateTime.now());
    e.setExpiresAt(LocalDateTime.now().plusDays(30));
    e.setClickCount(0);
    e.setIsActive(true);

    UrlEntity saved = urlRepository.save(e);

    mockMvc.perform(delete("/url/{id}", saved.getId()))
        .andExpect(status().isNoContent());

    org.assertj.core.api.Assertions.assertThat(urlRepository.findById(saved.getId())).isEmpty();
  }

  @Test
  void ShouldReturn404WhenDeletingNonExistingUrl() throws Exception {
    mockMvc.perform(delete("/url/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }
}
