package life.coachy.backend.infrastructure.query;

import com.querydsl.core.types.Predicate;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface QueryOperationsFactory {

  <E extends QueryDtoMarker> Set<E> obtainOperation(Predicate predicate, Pageable pageable, QueryFetchAllRepository<E> repository);

}
