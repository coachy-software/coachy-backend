package life.coachy.backend.headway.domain.dto;

import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;

public class HeadwayCreateCommandDto {

  private ObjectId identifier;
  private ObjectId ownerId;
  private Set<Double> measurements;
  private List<String> images;

  HeadwayCreateCommandDto() {}

  HeadwayCreateCommandDto(HeadwayCreateCommandDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.ownerId = builder.ownerId;
    this.measurements = builder.measurements;
    this.images = builder.images;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

  public Set<Double> getMeasurements() {
    return this.measurements;
  }

  public List<String> getImages() {
    return this.images;
  }

}
