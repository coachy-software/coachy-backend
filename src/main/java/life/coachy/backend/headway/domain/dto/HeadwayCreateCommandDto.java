package life.coachy.backend.headway.domain.dto;

import java.util.List;
import java.util.Set;
import life.coachy.backend.headway.measurement.dto.MeasurementDto;
import org.bson.types.ObjectId;

public class HeadwayCreateCommandDto {

  private ObjectId identifier;
  private ObjectId ownerId;
  private Set<MeasurementDto> measurements;
  private String type;
  private List<String> images;

  HeadwayCreateCommandDto() {}

  HeadwayCreateCommandDto(HeadwayCreateCommandDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.ownerId = builder.ownerId;
    this.measurements = builder.measurements;
    this.type = builder.type;
    this.images = builder.images;
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

  public Set<MeasurementDto> getMeasurements() {
    return this.measurements;
  }

  public String getType() {
    return this.type;
  }

  public List<String> getImages() {
    return this.images;
  }

}
