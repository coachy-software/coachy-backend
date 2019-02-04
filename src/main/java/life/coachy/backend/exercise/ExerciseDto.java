package life.coachy.backend.exercise;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.template.ExerciseTemplateDto;
import life.coachy.backend.util.dto.AbstractDto;

public class ExerciseDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private int sets;
  @NotNull(message = "{notNull}")
  private int reps;
  private int miniSets;
  private ExerciseTemplateDto template;

  public ExerciseDto(String name, int sets, int reps, int miniSets, ExerciseTemplateDto template) {
    this.name = name;
    this.sets = sets;
    this.reps = reps;
    this.miniSets = miniSets;
    this.template = template;
  }

  public ExerciseDto() {
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

  @Override
  public String getEntityName() {
    return this.name;
  }

}
