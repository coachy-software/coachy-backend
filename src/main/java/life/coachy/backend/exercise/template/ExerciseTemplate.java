package life.coachy.backend.exercise.template;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("exercises")
class ExerciseTemplate implements IdentifiableEntity<ObjectId> {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private List<String> exampleImages;
  private String briefDescription;

  ExerciseTemplate(ExerciseTemplateBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.exampleImages = builder.exampleImages;
    this.briefDescription = builder.briefDescription;
  }

  ExerciseTemplate() {}

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getExampleImages() {
    return this.exampleImages;
  }

  public void setExampleImages(List<String> exampleImages) {
    this.exampleImages = exampleImages;
  }

  public String getBriefDescription() {
    return this.briefDescription;
  }

  public void setBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return "ExerciseTemplate{" +
        "identifier=" + this.identifier +
        ", name='" + this.name + '\'' +
        ", exampleImages=" + this.exampleImages +
        ", briefDescription='" + this.briefDescription + '\'' +
        '}';
  }

}