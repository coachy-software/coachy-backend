package life.coachy.backend.exercise.template;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;
import org.bson.types.ObjectId;

public class ExerciseTemplateDto extends AbstractDto<ExerciseTemplate> {

  @NotNull(message = "{notNull}")
  private ObjectId identifier;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private List<String> exampleImages;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String briefDescription;
  @NotNull(message = "{notNull}")
  private boolean verified;

  public ExerciseTemplateDto(ObjectId identifier, String name, List<String> exampleImages, String briefDescription, boolean verified) {
    this.identifier = identifier;
    this.name = name;
    this.exampleImages = exampleImages;
    this.briefDescription = briefDescription;
    this.verified = verified;
  }

  public ExerciseTemplateDto() {
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

  public boolean isVerified() {
    return this.verified;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
