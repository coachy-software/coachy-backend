package life.coachy.backend.exercise.template.domain.dto;

import java.util.List;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ExerciseTemplateCommandDtoBuilder implements Buildable<ExerciseTemplateCommandDto> {

  ObjectId identifier;
  String name;
  List<String> exampleImages;
  String briefDescription;
  String muscleGroup;

  private ExerciseTemplateCommandDtoBuilder() {}

  public static ExerciseTemplateCommandDtoBuilder create() {
    return new ExerciseTemplateCommandDtoBuilder();
  }

  public ExerciseTemplateCommandDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ExerciseTemplateCommandDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseTemplateCommandDtoBuilder withExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
    return this;
  }

  public ExerciseTemplateCommandDtoBuilder withBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
    return this;
  }

  public ExerciseTemplateCommandDtoBuilder withMuscleGroup(String muscleGroup) {
    this.muscleGroup = muscleGroup;
    return this;
  }

  @Override
  public ExerciseTemplateCommandDto build() {
    return new ExerciseTemplateCommandDto(this);
  }

}
