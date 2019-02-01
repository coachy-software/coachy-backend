package life.coachy.backend.util;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudOperationsService<T extends IdentifiableEntity<ID>, ID> {

  Optional<T> findByName(String name);

  Optional<T> findById(ID id);

  List<T> findAll();

  <S extends T> S save(S entity);

  void deleteById(ID id);

  boolean existsById(ID id);

  List<T> findAll(Predicate predicate);

  Page<T> findAll(Predicate predicate, Pageable pageable);

}
