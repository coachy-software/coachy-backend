package life.coachy.backend.profile.recommendation.domain.dto;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class RecommendationCreateCommandDtoBuilder implements Buildable<RecommendationCreateCommandDto> {

  ObjectId profileUserId;
  ObjectId from;
  String content;
  int rating;

  private RecommendationCreateCommandDtoBuilder() {}

  public static RecommendationCreateCommandDtoBuilder create() {
    return new RecommendationCreateCommandDtoBuilder();
  }

  public RecommendationCreateCommandDtoBuilder withProfileUserId(ObjectId profileUserId) {
    this.profileUserId = profileUserId;
    return this;
  }

  public RecommendationCreateCommandDtoBuilder withFrom(ObjectId from) {
    this.from = from;
    return this;
  }

  public RecommendationCreateCommandDtoBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public RecommendationCreateCommandDtoBuilder withRating(int rating) {
    this.rating = rating;
    return this;
  }

  @Override
  public RecommendationCreateCommandDto build() {
    return new RecommendationCreateCommandDto(this);
  }

}
