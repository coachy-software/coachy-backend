package life.coachy.backend.exercise;

import life.coachy.backend.util.Buildable;

class ExerciseBuilder implements Buildable<Exercise> {

  String name;
  int sets;
  int reps;
  int miniSets;

  public ExerciseBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseBuilder withSets(int sets) {
    this.sets = sets;
    return this;
  }

  public ExerciseBuilder withReps(int reps) {
    this.reps = reps;
    return this;
  }

  public ExerciseBuilder withMiniSets(int miniSets) {
    this.miniSets = miniSets;
    return this;
  }

  @Override
  public Exercise build() {
    return new Exercise(this);
  }

}
