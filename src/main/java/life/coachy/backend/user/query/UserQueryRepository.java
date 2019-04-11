package life.coachy.backend.user.query;

import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;

public interface UserQueryRepository extends QueryFetchAllRepository<UserQueryDto, ObjectId>, QueryFetchOneRepository<UserQueryDto, ObjectId> {

  Optional<UserQueryDto> findByUsername(String username);

  Optional<UserQueryDto> findByEmail(String email);

  boolean existsByUsernameOrEmail(String username, String email);

  boolean existsByIdentifier(ObjectId id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

}
