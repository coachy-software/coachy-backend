package life.coachy.backend.exercise.template.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.template.ExerciseTemplateMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = ExerciseTemplateMapper.class, entityName = "ExerciseTemplate")
public class ExerciseTemplateUpdateDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private List<String> exampleImages;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String briefDescription;

  ExerciseTemplateUpdateDto(ExerciseTemplateUpdateDtoBuilder builder) {
    this.name = builder.name;
    this.exampleImages = builder.exampleImages;
    this.briefDescription = builder.briefDescription;
  }

  ExerciseTemplateUpdateDto() {}

  public String getName() {
    return this.name;
  }

  public List<String> getExampleImages() {
    return this.exampleImages;
  }

  public String getBriefDescription() {
    return this.briefDescription;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
