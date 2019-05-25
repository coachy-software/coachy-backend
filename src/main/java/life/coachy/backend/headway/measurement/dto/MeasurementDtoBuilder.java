package life.coachy.backend.headway.measurement.dto;

import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class MeasurementDtoBuilder implements Buildable<MeasurementDto> {

  ObjectId id;
  String name;
  String value;

  private MeasurementDtoBuilder() {}

  public static MeasurementDtoBuilder create() {
    return new MeasurementDtoBuilder();
  }

  public MeasurementDtoBuilder withId(ObjectId id) {
    this.id = id;
    return this;
  }

  public MeasurementDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public MeasurementDtoBuilder withValue(String value) {
    this.value = value;
    return this;
  }

  @Override
  public MeasurementDto build() {
    return new MeasurementDto(this);
  }

}
