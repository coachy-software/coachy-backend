package life.coachy.backend.headway.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import life.coachy.backend.headway.measurement.dto.MeasurementDto;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.HEADWAYS)
public class HeadwayQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId ownerId;
  private Set<MeasurementDto> measurements;
  private List<String> images;
  private String type;
  @JsonSerialize(using = ToStringSerializer.class) @CreatedDate private LocalDateTime createdAt;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

  public Set<MeasurementDto> getMeasurements() {
    return this.measurements;
  }

  public List<String> getImages() {
    return this.images;
  }

  public String getType() {
    return this.type;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

}
