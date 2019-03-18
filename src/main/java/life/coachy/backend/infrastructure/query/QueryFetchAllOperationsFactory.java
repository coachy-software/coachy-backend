package life.coachy.backend.infrastructure.query;

import com.google.common.base.Preconditions;
import com.querydsl.core.types.Predicate;
import java.util.Set;
import org.springframework.data.domain.Pageable;

class QueryFetchAllOperationsFactory implements QueryOperationsFactory {

  @Override
  public <E extends QueryDtoMarker> Set<E> obtainOperation(Predicate predicate, Pageable pageable, QueryFetchAllRepository<E> repository) {
    Preconditions.checkNotNull(repository, "Query fetch all repository cannot be null!");

    boolean isPredicatePresent = !(predicate == null);
    boolean isPaginationPresent = pageable.toOptional().isPresent();
    boolean isPagginationAndPredicatePresent = isPredicatePresent && isPaginationPresent;

    if (isPagginationAndPredicatePresent) {
      return repository.findAll(predicate, pageable);
    } else if (isPaginationPresent) {
      return repository.findAll(pageable);
    } else {
      return repository.findAll();
    }
  }


}
