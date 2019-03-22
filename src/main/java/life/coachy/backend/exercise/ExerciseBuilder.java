package life.coachy.backend.exercise;

import life.coachy.backend.exercise.template.dto.ExerciseTemplateDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

class ExerciseBuilder implements Buildable<Exercise> {

  ObjectId identifier;
  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplateDto template;

  private ExerciseBuilder() {}

  public static ExerciseBuilder createBuilder() {
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

  ExerciseBuilder withTemplate(ExerciseTemplateDto template) {
    this.template = template;
    return this;
  }

  @Override
  public Exercise build() {
    return new Exercise(this);
  }

}
