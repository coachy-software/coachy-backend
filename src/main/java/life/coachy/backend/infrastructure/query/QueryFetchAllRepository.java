package life.coachy.backend.infrastructure.query;

import com.querydsl.core.types.Predicate;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QueryFetchAllRepository<E extends QueryDtoMarker> {

  Set<E> findAll();

  Set<E> findAll(Predicate predicate, Pageable pageable);

  Set<E> findAll(Pageable pageable);

}
