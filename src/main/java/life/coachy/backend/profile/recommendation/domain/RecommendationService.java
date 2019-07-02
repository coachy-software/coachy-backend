package life.coachy.backend.profile.recommendation.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import life.coachy.backend.infrastructure.converter.PropertiesToMapConverter;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto;
import life.coachy.backend.profile.recommendation.domain.exception.RecommendationNotFoundException;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RecommendationService {

  private final RecommendationRepository recommendationRepository;
  private final RecommendationQueryRepository recommendationQueryRepository;
  private final PropertiesToMapConverter propertiesToMapConverter;
  private final UserFacade userFacade;

  @Autowired
  RecommendationService(RecommendationRepository recommendationRepository, RecommendationQueryRepository recommendationQueryRepository,
      PropertiesToMapConverter propertiesToMapConverter, UserFacade userFacade) {
    this.recommendationRepository = recommendationRepository;
    this.recommendationQueryRepository = recommendationQueryRepository;
    this.propertiesToMapConverter = propertiesToMapConverter;
    this.userFacade = userFacade;
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
    return this.recommendationQueryRepository.findById(recommendationId).orElseThrow(RecommendationNotFoundException::new);
  }

  Set<RecommendationQueryDto> fetchAllByProfileUserId(ObjectId profileUserId) {
    return this.recommendationQueryRepository.findAllByProfileUserIdOrderByRatingDesc(profileUserId);
  }

  Map<String, Object> convertAndAppendCreatorDetails(RecommendationQueryDto recommendationQueryDto, UserQueryDto creator) {
    Map<String, Object> convertedRecommendation = this.propertiesToMapConverter.convert(recommendationQueryDto);
    HashMap<Object, Object> fromSection = new HashMap<>();

    fromSection.put("username", creator.getUsername());
    fromSection.put("avatar", creator.getAvatar());
    fromSection.put("displayName", creator.getDisplayName());

    convertedRecommendation.remove("from");
    convertedRecommendation.put("from", fromSection);

    return convertedRecommendation;
  }

  Set<Map<String, Object>> convertAndAppendCreatorDetailsForEach(Set<RecommendationQueryDto> recommendationQueryDtos) {
    return recommendationQueryDtos.stream()
        .map(recommendation -> this.convertAndAppendCreatorDetails(recommendation, this.userFacade.fetchOne(recommendation.getFrom())))
        .collect(Collectors.toSet());
  }

}
