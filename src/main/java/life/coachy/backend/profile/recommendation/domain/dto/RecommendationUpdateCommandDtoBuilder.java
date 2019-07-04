package life.coachy.backend.profile.recommendation.domain.dto;

import life.coachy.backend.infrastructure.util.Buildable;

public final class RecommendationUpdateCommandDtoBuilder implements Buildable<RecommendationUpdateCommandDto> {

  String content;
  int rating;

  private RecommendationUpdateCommandDtoBuilder() {}

  public static RecommendationUpdateCommandDtoBuilder create() {
    return new RecommendationUpdateCommandDtoBuilder();
  }

  public RecommendationUpdateCommandDtoBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public RecommendationUpdateCommandDtoBuilder withRating(int rating) {
    this.rating = rating;
    return this;
  }

  @Override
  public RecommendationUpdateCommandDto build() {
    return new RecommendationUpdateCommandDto(this);
  }

}
