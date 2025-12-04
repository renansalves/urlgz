
package br.urlgz.app.repository;

import org.springframework.data.jpa.repository.*;
import br.urlgz.app.model.UrlEntity;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

  UrlEntity findByShortCode(String shortCode);


}

