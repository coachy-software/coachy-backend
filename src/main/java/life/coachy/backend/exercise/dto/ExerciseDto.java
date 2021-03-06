package life.coachy.backend.exercise.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDto;
import org.bson.types.ObjectId;

public class ExerciseDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @NotNull @NotEmpty private String name;
  @NotNull private int sets;
  @NotNull private int reps;
  private int miniSets;
  @NotNull private ExerciseTemplateCommandDto template;

  ExerciseDto() {}

  ExerciseDto(ExerciseDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
    this.template = builder.template;
  }

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
