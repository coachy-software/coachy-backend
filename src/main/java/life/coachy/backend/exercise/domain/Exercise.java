package life.coachy.backend.exercise.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

class Exercise {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String name;
  private int sets;
  private int reps;
  private int miniSets;
  private ExerciseTemplateCommandDto template;

  Exercise(ExerciseBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.sets = builder.sets;
    this.reps = builder.reps;
    this.miniSets = builder.miniSets;
    this.template = builder.template;
  }

  Exercise() {}

  public static ExerciseBuilder builder() {
    return ExerciseBuilder.create();
  }

}
