package life.coachy.backend.headway.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface HeadwayQueryRepository extends QueryFetchOneRepository<HeadwayQueryDto, ObjectId>, Repository<HeadwayQueryDto, ObjectId> {

  Set<HeadwayQueryDto> fetchAllByOwnerIdAndOrderByCreatedAtDesc(ObjectId ownerId);

}
