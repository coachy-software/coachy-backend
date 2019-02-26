package life.coachy.backend.exercise.template.dto;

import java.util.List;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public final class ExerciseTemplateDtoBuilder implements Buildable<ExerciseTemplateDto> {

  ObjectId identifier;
  String name;
  List<String> exampleImages;
  String briefDescription;

  private ExerciseTemplateDtoBuilder() {}

  public static ExerciseTemplateDtoBuilder createBuilder() {
    return new ExerciseTemplateDtoBuilder();
  }

  public ExerciseTemplateDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ExerciseTemplateDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseTemplateDtoBuilder withExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
    return this;
  }

  public ExerciseTemplateDtoBuilder withBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
    return this;
  }

  @Override
  public ExerciseTemplateDto build() {
    return new ExerciseTemplateDto(this);
  }

}
