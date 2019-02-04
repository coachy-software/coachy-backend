package life.coachy.backend.exercise.template;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.dto.AbstractDto;

public class ExerciseTemplateUpdateDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private List<String> exampleImages;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String briefDescription;
  @NotNull(message = "{notNull}")
  private boolean verified;

  public ExerciseTemplateUpdateDto(String name, List<String> exampleImages, String briefDescription, boolean verified) {
    this.name = name;
    this.exampleImages = exampleImages;
    this.briefDescription = briefDescription;
    this.verified = verified;
  }

  public ExerciseTemplateUpdateDto() {
  }

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

}
