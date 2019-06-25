package life.coachy.backend.profile.recommendation.domain;

import life.coachy.backend.infrastructure.command.CommandRepository;
import org.bson.types.ObjectId;

interface RecommendationRepository extends CommandRepository<Recommendation, ObjectId> {

}
