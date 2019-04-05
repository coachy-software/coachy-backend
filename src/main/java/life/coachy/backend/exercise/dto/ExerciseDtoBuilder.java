package life.coachy.backend.exercise.dto;

import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ExerciseDtoBuilder implements Buildable<ExerciseDto> {

  ObjectId identifier;
  String name;
  int sets;
  int reps;
  int miniSets;
  ExerciseTemplateCommandDto template;

  private ExerciseDtoBuilder() {}

  public static ExerciseDtoBuilder create() {
    return new ExerciseDtoBuilder();
  }

  public ExerciseDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ExerciseDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ExerciseDtoBuilder withSets(int sets) {
    this.sets = sets;
    return this;
  }

  public ExerciseDtoBuilder withReps(int reps) {
    this.reps = reps;
    return this;
  }

  public ExerciseDtoBuilder withMiniSets(int miniSets) {
    this.miniSets = miniSets;
    return this;
  }

  public ExerciseDtoBuilder withTemplate(ExerciseTemplateCommandDto template) {
    this.template = template;
    return this;
  }

  @Override
  public ExerciseDto build() {
    return new ExerciseDto(this);
  }

}
