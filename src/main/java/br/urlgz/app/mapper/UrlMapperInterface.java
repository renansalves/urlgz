package br.urlgz.app.mapper;

import org.mapstruct.Mapper;
import br.urlgz.app.model.UrlEntity;
import br.urlgz.app.dto.UrlResponse;
import br.urlgz.app.dto.UrlRequest;

@Mapper(componentModel = "spring")
public interface UrlMapperInterface {

  UrlResponse responseToDto(UrlEntity urlModel);

  UrlEntity toEntity(UrlResponse urlResponse);

  UrlRequest RequestToDto(UrlEntity urlModel);

  UrlEntity toEntity(UrlRequest urlRequest);

}
