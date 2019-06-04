package life.coachy.backend.request.query;

import java.util.Optional;
import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface RequestQueryRepository extends QueryFetchOneRepository<RequestQueryDto, ObjectId>, Repository<RequestQueryDto, ObjectId> {

  boolean existsByToken(String token);

  Optional<RequestQueryDto> findByToken(String token);

  Set<RequestQueryDto> findAllByRequesterId(ObjectId requesterId);

}
