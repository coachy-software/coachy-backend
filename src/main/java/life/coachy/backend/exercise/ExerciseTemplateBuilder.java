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

  public ExerciseTemplateBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseTemplateBuilder withExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
    return this;
  }

  public ExerciseTemplateBuilder withBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
    return this;
  }

  public ExerciseTemplateBuilder withVerified(boolean verified) {
    this.verified = verified;
    return this;
  }

  @Override
  public ExerciseTemplate build() {
    return new ExerciseTemplate(this);
  }

}
