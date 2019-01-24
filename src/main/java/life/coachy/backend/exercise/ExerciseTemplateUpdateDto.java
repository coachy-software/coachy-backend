package life.coachy.backend.exercise;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;

public class ExerciseTemplateUpdateDto extends AbstractDto<ExerciseTemplate> {

  @NotNull(message = "{notNull}")
  @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  @NotEmpty(message = "{notEmpty}")
  private List<String> exampleImages;
  @NotNull(message = "{notNull}")
  @NotEmpty(message = "{notEmpty}")
  private String briefDescription;
  @NotNull(message = "{notNull}")
  private boolean verified;

  public String getEntityName() {
    return this.name;
  }

  public List<String> getExampleImages() {
    return this.exampleImages;
  }

  public String getBriefDescription() {
    return this.briefDescription;
  }

  public boolean isVerified() {
    return this.verified;
  }

  @Override
  public ExerciseTemplate toEntity() {
    return new ExerciseTemplateBuilder()
        .withName(this.name)
        .withExampleImages(this.exampleImages)
        .withBriefDescription(this.briefDescription)
        .withVerified(this.verified)
        .build();
  }

}
