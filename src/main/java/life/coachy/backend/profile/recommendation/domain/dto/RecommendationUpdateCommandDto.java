package life.coachy.backend.profile.recommendation.domain.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RecommendationUpdateCommandDto {

  @NotNull @NotEmpty private String content;
  @NotNull private int rating;
  @NotNull private boolean visible;

  RecommendationUpdateCommandDto() {}

  RecommendationUpdateCommandDto(RecommendationUpdateCommandDtoBuilder builder) {
    this.content = builder.content;
    this.rating = builder.rating;
    this.visible = builder.visible;
  }

  public String getContent() {
    return this.content;
  }

  public int getRating() {
    return this.rating;
  }

  public boolean isVisible() {
    return this.visible;
  }

}
