package life.coachy.backend.exercise.template.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.EXERCISES)
public class ExerciseTemplateQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private List<String> exampleImages;
  private String briefDescription;
  private String muscleGroup;

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
