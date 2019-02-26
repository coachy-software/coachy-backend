package life.coachy.backend.exercise.template.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.template.ExerciseTemplateMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = ExerciseTemplateMapper.class, entityName = "ExerciseTemplate")
public class ExerciseTemplateDto extends AbstractDto {

  @NotNull(message = "{notNull}")
  private ObjectId identifier;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private List<String> exampleImages;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String briefDescription;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String muscleGroup;

  ExerciseTemplateDto(ExerciseTemplateDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.exampleImages = builder.exampleImages;
    this.briefDescription = builder.briefDescription;
    this.muscleGroup = builder.muscleGroup;
  }

  ExerciseTemplateDto() {}

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

  @Override
  public String getEntityName() {
    return this.name;
  }

}
