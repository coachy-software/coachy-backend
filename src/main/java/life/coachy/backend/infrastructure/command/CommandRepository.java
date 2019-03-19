package life.coachy.backend.infrastructure.command;

import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CommandRepository<E, ID extends Serializable> extends Repository<E, ID> {

  <S extends E> S save(S entity);

  <S extends E> Iterable<S> saveAll(Iterable<S> entities);

  void deleteById(ID id);

  void delete(E entity);

  void deleteAll(Iterable<? extends E> entities);

  void deleteAll();

}
