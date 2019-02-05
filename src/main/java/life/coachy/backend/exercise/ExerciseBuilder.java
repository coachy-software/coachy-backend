package life.coachy.backend.exercise;

import life.coachy.backend.exercise.template.ExerciseTemplateDto;
import life.coachy.backend.util.Buildable;

class ExerciseBuilder implements Buildable<Exercise> {

  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplateDto template;

  private ExerciseBuilder() {}

  public static ExerciseBuilder createBuilder() {
    return new ExerciseBuilder();
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
