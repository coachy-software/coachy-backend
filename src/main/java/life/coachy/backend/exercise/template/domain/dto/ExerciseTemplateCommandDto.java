package life.coachy.backend.exercise.template.domain.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import org.bson.types.ObjectId;

public class ExerciseTemplateCommandDto implements CommandDtoMarker {

  @NotNull private ObjectId identifier;
  @NotNull @NotEmpty private String name;
  @NotNull @NotEmpty private List<String> exampleImages;
  @NotNull @NotEmpty private String briefDescription;
  @NotNull @NotEmpty private String muscleGroup;

  ExerciseTemplateCommandDto() {}

  ExerciseTemplateCommandDto(ExerciseTemplateCommandDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.exampleImages = builder.exampleImages;
    this.briefDescription = builder.briefDescription;
    this.muscleGroup = builder.muscleGroup;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public List<String> getExampleImages() {
    return this.exampleImages;
  }

  public String getBriefDescription() {
    return this.briefDescription;
  }

  public String getMuscleGroup() {
    return this.muscleGroup;
  }

}
