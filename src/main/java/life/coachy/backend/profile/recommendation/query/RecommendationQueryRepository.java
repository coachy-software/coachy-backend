package life.coachy.backend.profile.recommendation.query;

import java.util.Set;
import life.coachy.backend.infrastructure.query.QueryFetchOneRepository;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

public interface RecommendationQueryRepository extends QueryFetchOneRepository<RecommendationQueryDto, ObjectId>, Repository<RecommendationQueryDto, ObjectId> {

  Set<RecommendationQueryDto> findAllByProfileUserIdOrderByRatingDesc(ObjectId profileUserId);

}
