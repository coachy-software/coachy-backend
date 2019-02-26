package life.coachy.backend.exercise.template.dto;

import java.util.List;
import life.coachy.backend.util.Buildable;

public final class ExerciseTemplateUpdateDtoBuilder implements Buildable<ExerciseTemplateUpdateDto> {

  String name;
  List<String> exampleImages;
  String briefDescription;
  String muscleGroup;

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

  public ExerciseTemplateUpdateDtoBuilder withMuscleGroup(String muscleGroup) {
    this.muscleGroup = muscleGroup;
    return this;
  }

  public ExerciseTemplateUpdateDto build() {
    return new ExerciseTemplateUpdateDto(this);
  }

}
