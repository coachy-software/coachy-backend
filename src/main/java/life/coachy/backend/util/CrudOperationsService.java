package life.coachy.backend.util;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.dto.AbstractDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudOperationsService<T extends IdentifiableEntity<ID>, ID> {

  Optional<T> findByName(String name);

  Optional<T> findById(ID id);

  <S> List<S> findAll();

  T save(T entity);

  <S extends AbstractDto> T save(S dto);

  void deleteById(ID id);

  boolean existsById(ID id);

  <S> List<S> findAll(Predicate predicate);

  <S> Page<S> findAll(Pageable pageable);

  <S> Page<S> findAll(Predicate predicate, Pageable pageable);

}
