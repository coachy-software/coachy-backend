package life.coachy.backend.exercise;

import life.coachy.backend.util.Buildable;

class ExerciseBuilder implements Buildable<Exercise> {

  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplate template;

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

  ExerciseBuilder withTemplate(ExerciseTemplate template) {
    this.template = template;
    return this;
  }

  @Override
  public Exercise build() {
    return new Exercise(this);
  }

}
