package life.coachy.backend.user.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface UserQueryRepository extends Repository<UserQueryDto, ObjectId>, QueryFetchAllRepository<UserQueryDto> {

  Set<UserQueryDto> findAll();

}
