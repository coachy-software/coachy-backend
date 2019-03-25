package life.coachy.backend.exercise.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDto;
import org.bson.types.ObjectId;

public class ExerciseDto {

  private ObjectId identifier;
  @NotNull @NotEmpty private String name;
  @NotNull private int sets;
  @NotNull private int reps;
  private int miniSets;
  private ExerciseTemplateCommandDto template;

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

  public ExerciseTemplateCommandDto getTemplate() {
    return this.template;
  }

}
