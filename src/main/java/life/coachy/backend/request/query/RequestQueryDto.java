package life.coachy.backend.request.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.REQUESTS)
public class RequestQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String token;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId requesterId;
  @JsonSerialize(using = ToStringSerializer.class) private LocalDateTime createdAt;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getToken() {
    return this.token;
  }

  public ObjectId getRequesterId() {
    return this.requesterId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

}
