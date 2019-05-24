package life.coachy.backend.headway.domain.dto;

import java.util.List;
import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class HeadwayCreateCommandDtoBuilder implements Buildable<HeadwayCreateCommandDto> {

  ObjectId identifier;
  ObjectId ownerId;
  Set<Double> measurements;
  List<String> images;

  private HeadwayCreateCommandDtoBuilder() {}

  public static HeadwayCreateCommandDtoBuilder create() {
    return new HeadwayCreateCommandDtoBuilder();
  }

  public HeadwayCreateCommandDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withMeasurements(Set<Double> measurements) {
    this.measurements = measurements;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withImages(List<String> images) {
    this.images = images;
    return this;
  }

  @Override
  public HeadwayCreateCommandDto build() {
    return new HeadwayCreateCommandDto(this);
  }

}
