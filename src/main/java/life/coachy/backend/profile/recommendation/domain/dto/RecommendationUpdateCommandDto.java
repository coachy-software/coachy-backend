package life.coachy.backend.profile.recommendation.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RecommendationUpdateCommandDto {

  @NotNull @NotEmpty private String content;
  @NotNull private int rating;

  RecommendationUpdateCommandDto() {}

  RecommendationUpdateCommandDto(RecommendationUpdateCommandDtoBuilder builder) {
    this.content = builder.content;
    this.rating = builder.rating;
  }

  public String getContent() {
    return this.content;
  }

  public int getRating() {
    return this.rating;
  }

}
