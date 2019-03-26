package life.coachy.backend.board.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import life.coachy.backend.board.label.LabelDto;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.BOARDS)
public class BoardQueryDto implements QueryDtoMarker {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId identifier;
  private String name;
  private List<LabelDto> labels;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId ownerId;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public List<LabelDto> getLabels() {
    return this.labels;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

}
