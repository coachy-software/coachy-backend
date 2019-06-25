package life.coachy.backend.profile.recommendation.domain;

import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class RecommendationBuilder implements Buildable<Recommendation> {

  ObjectId id;
  ObjectId profileUserId;
  ObjectId from;
  String content;
  int rating;
  boolean visible;
  LocalDateTime createdAt;
  LocalDateTime lastModifiedDate;

  private RecommendationBuilder() {}

  public static RecommendationBuilder create() {
    return new RecommendationBuilder();
  }

  public RecommendationBuilder withId(ObjectId id) {
    this.id = id;
    return this;
  }

  public RecommendationBuilder withProfileUserId(ObjectId profileUserId) {
    this.profileUserId = profileUserId;
    return this;
  }

  public RecommendationBuilder withFrom(ObjectId from) {
    this.from = from;
    return this;
  }

  public RecommendationBuilder withContent(String content) {
    this.content = content;
    return this;
  }

  public RecommendationBuilder withRating(int rating) {
    this.rating = rating;
    return this;
  }

  public RecommendationBuilder withVisible(boolean visible) {
    this.visible = visible;
    return this;
  }

  public RecommendationBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public RecommendationBuilder withLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

  @Override
  public Recommendation build() {
    return new Recommendation(this);
  }

}
