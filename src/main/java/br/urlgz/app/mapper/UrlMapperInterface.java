package br.urlgz.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;

@Mapper(componentModel = "spring")
public interface UrlMapperInterface {

  /** Convert from UrlEntity to UrlResponse
   * @param UrlEntity
   * @return UrlResponse
   */
  UrlResponse responseToDto(UrlEntity urlModel);

  /** Convert from UrlEntity DtoRequest
   * @param UrlEntity
   * @return UrlRequest
   */
  @Mapping(source = "originalUrl", target = "url")
  UrlRequest requestToDto(UrlEntity urlModel);

  /** Convert from UrlEntity to UrlResponse
   * @param UrlRequest
   * @return UrlEntity
   */
  @Mapping(source = "url", target = "originalUrl")
  UrlEntity toEntity(UrlRequest urlRequest);

}
