package br.urlgz.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;

@Mapper(componentModel = "spring")
public interface UrlMapperInterface {

  UrlResponse responseToDto(UrlEntity urlModel);

  @Mapping(source = "originalUrl", target = "url")
  UrlRequest requestToDto(UrlEntity urlModel);

  @Mapping(source = "url", target = "originalUrl")
  UrlEntity toEntity(UrlRequest urlRequest);

}
