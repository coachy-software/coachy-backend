package life.coachy.backend.infrastructure.query;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QueryFetchOneRepository<E extends QueryDtoMarker, ID extends Serializable> {

  Optional<E> findById(ID id);

}
