package life.coachy.backend.exercise.template.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.EXERCISES)
class ExerciseTemplate {

  @Id @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private List<String> exampleImages;
  private String briefDescription;
  private String muscleGroup;

}
