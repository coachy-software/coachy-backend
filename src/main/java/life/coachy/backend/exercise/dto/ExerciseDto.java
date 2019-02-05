package life.coachy.backend.exercise.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.ExerciseMapper;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = ExerciseMapper.class, entityName = "Exercise")
public class ExerciseDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private int sets;
  @NotNull(message = "{notNull}")
  private int reps;
  private int miniSets;
  private ExerciseTemplateDto template;

  ExerciseDto(ExerciseDtoBuilder builder) {
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
    this.template = builder.template;
  }

  ExerciseDto() {}

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
