package life.coachy.backend.exercise.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.old_exercise.template.dto.ExerciseTemplateDto;
import org.bson.types.ObjectId;

public class ExerciseDto {

  private ObjectId identifier;
  @NotNull @NotEmpty private String name;
  @NotNull private int sets;
  @NotNull private int reps;
  private int miniSets;
  private ExerciseTemplateDto template;

  ExerciseDto(ExerciseDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
    this.template = builder.template;
  }

  ExerciseDto() {}

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public int getSets() {
    return this.sets;
  }

  public int getReps() {
    return this.reps;
  }

  public int getMiniSets() {
    return this.miniSets;
  }

  public ExerciseTemplateDto getTemplate() {
    return this.template;
  }

}
