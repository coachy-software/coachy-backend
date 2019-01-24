package life.coachy.backend.exercise;

import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

class ExerciseBuilder implements Buildable<Exercise> {

  ObjectId identifier;
  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplate template;

  public ExerciseBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

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

  public ExerciseBuilder withTemplate(ExerciseTemplate template) {
    this.template = template;
    return this;
  }

  @Override
  public Exercise build() {
    return new Exercise(this);
  }

}
