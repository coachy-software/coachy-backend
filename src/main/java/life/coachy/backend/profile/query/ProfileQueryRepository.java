package life.coachy.backend.profile.query;

import java.util.Optional;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface ProfileQueryRepository extends QueryFetchOneRepository<ProfileQueryDto, ObjectId>, Repository<ProfileQueryDto, ObjectId> {

  Optional<ProfileQueryDto> findByUserId(ObjectId userId);

}
