package life.coachy.backend.util;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public final class PredicateResponseFactory {

  private PredicateResponseFactory() {
  }

  public static ResponseEntity<?> obtainResponse(Predicate predicate, Pageable pageable, CrudOperationsService<?, ?> crudService) {
    boolean isPredicatePresent = !(predicate == null);
    boolean isPaginationPresent = pageable.toOptional().isPresent();
    boolean isPagginationAndPredicatePresent = isPredicatePresent && isPaginationPresent;

    if (isPagginationAndPredicatePresent) {
      return ResponseEntity.ok(crudService.findAll(predicate, pageable));
    } else if (isPaginationPresent) {
      return ResponseEntity.ok(crudService.findAll(pageable));
    } else {
      return ResponseEntity.ok(crudService.findAll());
    }
  }

}
