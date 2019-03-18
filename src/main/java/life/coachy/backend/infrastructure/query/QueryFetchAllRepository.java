package life.coachy.backend.infrastructure.query;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface QueryFetchAllRepository<E extends QueryDtoMarker, ID extends Serializable> extends Repository<E, ID>, QuerydslPredicateExecutor<E> {

  List<E> findAll();

  Page<E> findAll(Pageable pageable);

}
