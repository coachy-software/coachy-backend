package life.coachy.backend.profile.query;

import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchAllRepository;
import org.bson.types.ObjectId;

public interface ProfileQueryRepository extends QueryFetchAllRepository<ProfileQueryDto, ObjectId> {

  Optional<ProfileQueryDto> findByUserId(ObjectId userId);

}
