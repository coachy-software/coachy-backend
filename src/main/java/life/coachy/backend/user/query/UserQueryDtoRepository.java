package life.coachy.backend.user.query;

import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;

public interface UserQueryDtoRepository extends QueryFetchAllRepository<UserQueryDto, ObjectId>, QueryFetchOneRepository<UserQueryDto, ObjectId> {

  Optional<UserQueryDto> findByUsername(String username);

  boolean existsByUsernameOrEmail(String username, String email);

}
