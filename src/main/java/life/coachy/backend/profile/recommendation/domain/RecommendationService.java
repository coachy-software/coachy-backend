package life.coachy.backend.profile.recommendation.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto;
import life.coachy.backend.profile.recommendation.domain.exception.RecommendationNotFound;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RecommendationService {

  private final RecommendationRepository recommendationRepository;
  private final RecommendationQueryRepository recommendationQueryRepository;
  private final ObjectToJsonConverter toJsonConverter;

  @Autowired
  RecommendationService(RecommendationRepository recommendationRepository, RecommendationQueryRepository recommendationQueryRepository,
      ObjectToJsonConverter toJsonConverter) {
    this.recommendationRepository = recommendationRepository;
    this.recommendationQueryRepository = recommendationQueryRepository;
    this.toJsonConverter = toJsonConverter;
  }

  void commitChange(Recommendation recommendation, RecommendationUpdateCommandDto dto) {
    recommendation.setContent(dto.getContent());
    recommendation.setRating(dto.getRating());
    recommendation.setVisible(dto.isVisible());

    this.save(recommendation);
  }

  void updateStatus(Recommendation recommendation, boolean status) {
    recommendation.setVisible(status);
    this.save(recommendation);
  }

  Recommendation save(Recommendation recommendation) {
    return this.recommendationRepository.save(recommendation);
  }

  RecommendationQueryDto fetchOneOrThrow(ObjectId recommendationId) {
    return this.recommendationQueryRepository.findById(recommendationId).orElseThrow(RecommendationNotFound::new);
  }

  Set<RecommendationQueryDto> fetchAllByProfileUserId(ObjectId profileUserId) {
    return this.recommendationQueryRepository.findAllByProfileUserIdOrderByRatingDesc(profileUserId);
  }

}
