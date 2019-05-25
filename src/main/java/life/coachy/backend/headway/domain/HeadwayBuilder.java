package life.coachy.backend.headway.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import life.coachy.backend.headway.measurement.dto.MeasurementDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class HeadwayBuilder implements Buildable<Headway> {

  ObjectId identifier;
  ObjectId ownerId;
  Set<MeasurementDto> measurements;
  List<String> images;
  LocalDateTime createdAt;

  private HeadwayBuilder() {}

  static HeadwayBuilder create() {
    return new HeadwayBuilder();
  }

  HeadwayBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  HeadwayBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  HeadwayBuilder withMeasurements(Set<MeasurementDto> measurements) {
    this.measurements = measurements;
    return this;
  }

  HeadwayBuilder withImages(List<String> images) {
    this.images = images;
    return this;
  }

  HeadwayBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public Headway build() {
    return new Headway(this);
  }

}
