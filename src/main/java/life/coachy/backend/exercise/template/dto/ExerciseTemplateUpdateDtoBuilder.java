package life.coachy.backend.exercise.template.dto;

import java.util.List;
import life.coachy.backend.util.Buildable;

public final class ExerciseTemplateUpdateDtoBuilder implements Buildable<ExerciseTemplateUpdateDto> {

  String name;
  List<String> exampleImages;
  String briefDescription;
  boolean verified;

  private ExerciseTemplateUpdateDtoBuilder() {}

  public static ExerciseTemplateUpdateDtoBuilder createBuilder() {
    return new ExerciseTemplateUpdateDtoBuilder();
  }

  public ExerciseTemplateUpdateDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseTemplateUpdateDtoBuilder withExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
    return this;
  }

  public ExerciseTemplateUpdateDtoBuilder withBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
    return this;
  }

  public ExerciseTemplateUpdateDtoBuilder withVerified(boolean verified) {
    this.verified = verified;
    return this;
  }

  public ExerciseTemplateUpdateDto build() {
    return new ExerciseTemplateUpdateDto(this);
  }

}
