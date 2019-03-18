package life.coachy.backend.user.query;

import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import org.bson.types.ObjectId;

public interface UserQueryDtoRepository extends QueryFetchAllRepository<UserQueryDto, ObjectId> {

}
