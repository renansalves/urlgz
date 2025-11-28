package br.urlgz.app.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;
import br.urlgz.app.utils.Base62;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.repository.UrlRepository;
import br.urlgz.app.mapper.UrlMapperInterface;

/**
 * Classe UrlService, responsavel em proverer as principais funcionalidades para
 * encurtar urls.
 * 
 * @author Renan Alves
 * @version 1.0
 */
@Service
public class UrlService {

  @Autowired
  private UrlRepository urlRepository;

  @Autowired
  private UrlMapperInterface urlMapperInterface;

  private static final String BASESHORTURL = "https://localhost:8080/";

  /**
   * Metodo responsavel pela codificação da URL.
   * Realiza a codificação da url utilizando a classe base62
   * 
   * @see {@link br.urlgz.app.utils.Base62}
   * 
   * @author Renan Alves
   *
   * @param urlRequest String - String contendo a url a ser encurtada.
   * @param expiresIn  LocalDateTime- Data de expiração do link encurtado.
   *
   *                   <p>
   *                   Valor de retorno da chamada POST /api/v1/shorten.
   * 
   *                   <pre>{@code
   *  {
   *    "url": "https://exemplo.com/pagina-muito-longa",
   *    "expiresIn": "30d" // opcional
   *  }
   * }</pre>
   *
   */

  public UrlResponse urlShortEncode(UrlRequest urlRequest) {

    UrlEntity urlEntity = urlMapperInterface.toEntity(urlRequest);

    urlEntity.setCreatedAt(LocalDateTime.now());
    urlEntity.setExpiresAt(LocalDateTime.now().plusDays(30));


    UrlEntity savedEntity = urlRepository.save(urlEntity);
    

    long obfuscatedId = savedEntity.getId() * 1000L + ThreadLocalRandom.current().nextInt(1000, 999999999);

    urlEntity.setShortCode(Base62.encode(obfuscatedId));
    
    UrlResponse urlResponse = urlMapperInterface.responseToDto(savedEntity);

    String shortUrl = BASESHORTURL + urlEntity.getShortCode();

    return new UrlResponse(
        urlResponse.shortCode(),
        urlResponse.originalUrl(),
        shortUrl,
        urlResponse.createdAt(),
        urlResponse.totalClicks()
    );

  }

  /**
   * Metodo responsavel pela consulta da URL curta, e redirecionamento da url
   * original.
   * 
   * @author Renan Alves
   * @param shortCode        String - String contendo a url a ser encurtada.
   * @param originalUrl      String - Data de expiração do link encurtado.
   * @param createdAt        LocalDateTime - Data de expiração do link encurtado.
   * @param totalClicks      longlong - Data de expiração do link encurtado.
   * @param clicksLast30Days Long - Data de expiração do link encurtado.
   *                         ### **GET /api/urls/{shortCode}**
   * @return
   *         ```json
   *         {
   *         "shortCode": "abc123",
   *         "originalUrl": "https://...",
   *         "createdAt": "2024-01-01T00:00:00Z",
   *         "totalClicks": 150,
   *         "clicksLast30Days": 45
   *         }
   *         ```
   */

  public UrlResponse searchOriginalUrl(String shortUrl) {

    UrlEntity savedUrl = urlRepository.findByShortCode(shortUrl);
    return null;
  }

  /**
   * Metodo responsavel pela remoção dos registros no banco.
   * 
   * @author Renan Alves
   * @param shortCode String - String contendo a url a ser encurtada.
   *                  ```json
   *                  {
   *                  "shortCode": "abc123",
   *                  }
   *                  ```
   */
  public void deleteShortlUrlData(Long id) {

  }
}
