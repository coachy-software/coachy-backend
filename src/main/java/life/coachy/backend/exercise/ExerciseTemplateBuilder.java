package life.coachy.backend.exercise;

import java.util.List;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

class ExerciseTemplateBuilder implements Buildable<ExerciseTemplate> {

  ObjectId identifier;
  String name;
  List<String> exampleImages;
  String briefDescription;
  boolean verified;

  public ExerciseTemplateBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  ExerciseTemplateBuilder withName(String name) {
    this.name = name;
    return this;
  }

  ExerciseTemplateBuilder withExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
    return this;
  }

  ExerciseTemplateBuilder withBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
    return this;
  }

  ExerciseTemplateBuilder withVerified(boolean verified) {
    this.verified = verified;
    return this;
  }

  @Override
  public ExerciseTemplate build() {
    return new ExerciseTemplate(this);
  }

}
