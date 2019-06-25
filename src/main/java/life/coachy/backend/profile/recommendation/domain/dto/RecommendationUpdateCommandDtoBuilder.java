package life.coachy.backend.profile.recommendation.domain.dto;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;

public final class RecommendationUpdateCommandDtoBuilder implements Buildable<RecommendationUpdateCommandDto> {

  String content;
  int rating;
  boolean visible;

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

  public RecommendationUpdateCommandDtoBuilder withVisible(boolean visible) {
    this.visible = visible;
    return this;
  }

  @Override
  public RecommendationUpdateCommandDto build() {
    return new RecommendationUpdateCommandDto(this);
  }

}
