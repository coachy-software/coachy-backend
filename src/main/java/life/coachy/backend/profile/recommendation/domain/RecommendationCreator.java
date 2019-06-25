package life.coachy.backend.profile.recommendation.domain;

import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;

class RecommendationCreator {

  Recommendation from(RecommendationCreateCommandDto dto) {
    return RecommendationBuilder.create()
        .withProfileUserId(dto.getProfileUserId())
        .withFrom(dto.getFrom())
        .withContent(dto.getContent())
        .withRating(dto.getRating())
        .build();
  }

  Recommendation from(RecommendationQueryDto dto) {
    return RecommendationBuilder.create()
        .withId(dto.getId())
        .withProfileUserId(dto.getProfileUserId())
        .withFrom(dto.getFrom())
        .withContent(dto.getContent())
        .withRating(dto.getRating())
        .withVisible(dto.isVisible())
        .withCreatedAt(dto.getCreatedAt())
        .withLastModifiedDate(dto.getLastModifiedDate())
        .build();
  }

}
