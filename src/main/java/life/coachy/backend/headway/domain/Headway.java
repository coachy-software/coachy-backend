package life.coachy.backend.headway.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import life.coachy.backend.headway.measurement.dto.MeasurementDto;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.HEADWAYS)
class Headway {

  @Id ObjectId identifier;
  private ObjectId ownerId;
  private Set<MeasurementDto> measurements;
  private List<String> images;
  private HeadwayType type;
  @CreatedDate private LocalDateTime createdAt;

  Headway() {}

  Headway(HeadwayBuilder builder) {
    this.identifier = builder.identifier;
    this.ownerId = builder.ownerId;
    this.measurements = builder.measurements;
    this.images = builder.images;
    this.type = builder.type;
    this.createdAt = builder.createdAt;
  }

}
