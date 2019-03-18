package life.coachy.backend.infrastructure.query;

import com.google.common.base.Preconditions;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Pageable;

class QueryFetchAllOperationsFactory implements QueryOperationsFactory {

  @Override
  public <E extends QueryDtoMarker> List<E> obtainOperation(Predicate predicate, Pageable pageable, QueryFetchAllRepository<E, ?> repository) {
    Preconditions.checkNotNull(repository, "Query fetch all repository cannot be null!");

    boolean isPredicatePresent = !(predicate == null);
    boolean isPaginationPresent = pageable.toOptional().isPresent();
    boolean isPagginationAndPredicatePresent = isPredicatePresent && isPaginationPresent;

    if (isPagginationAndPredicatePresent) {
      return repository.findAll(predicate, pageable).getContent();
    } else if (isPaginationPresent) {
      return repository.findAll(pageable).getContent();
    } else {
      return repository.findAll();
    }
  }


}
