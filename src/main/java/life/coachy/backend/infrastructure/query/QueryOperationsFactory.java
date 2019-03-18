package life.coachy.backend.infrastructure.query;

import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface QueryOperationsFactory {

  <E extends QueryDtoMarker> List<E> obtainOperation(Predicate predicate, Pageable pageable, QueryFetchAllRepository<E, ?> repository);

}
