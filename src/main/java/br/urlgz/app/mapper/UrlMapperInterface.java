package br.urlgz.app.mapper;

import org.mapstruct.Mapper;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;

@Mapper(componentModel = "spring")
public interface UrlMapperInterface {

  UrlEntity toDto(UrlEntity urlModel);

  UrlResponse toEntity(UrlResponse urlResponse);

  UrlRequest toEntity(UrlRequest urlRequest);

}
