package life.coachy.backend.exercise.domain;

import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class ExerciseBuilder implements Buildable<Exercise> {

  ObjectId identifier;
  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplateCommandDto template;

  private ExerciseBuilder() {}

  public static ExerciseBuilder create() {
    return new ExerciseBuilder();
  }

  ExerciseBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  ExerciseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  ExerciseBuilder withSets(int sets) {
    this.sets = sets;
    return this;
  }

  ExerciseBuilder withReps(int reps) {
    this.reps = reps;
    return this;
  }

  ExerciseBuilder withMiniSets(int miniSets) {
    this.miniSets = miniSets;
    return this;
  }

  ExerciseBuilder withTemplate(ExerciseTemplateCommandDto template) {
    this.template = template;
    return this;
  }

  @Override
  public Exercise build() {
    return new Exercise(this);
  }

}
