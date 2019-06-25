package life.coachy.backend.profile.recommendation.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.bson.types.ObjectId;

public class RecommendationCreateCommandDto {

  @NotNull private ObjectId profileUserId;
  @NotNull private ObjectId from;
  @NotNull @NotEmpty private String content;
  @NotNull private int rating;

  RecommendationCreateCommandDto() {}

  RecommendationCreateCommandDto(RecommendationCreateCommandDtoBuilder builder) {
    this.profileUserId = builder.profileUserId;
    this.from = builder.from;
    this.content = builder.content;
    this.rating = builder.rating;
  }

  public ObjectId getProfileUserId() {
    return this.profileUserId;
  }

  public ObjectId getFrom() {
    return this.from;
  }

  public String getContent() {
    return this.content;
  }

  public int getRating() {
    return this.rating;
  }

}
