package life.coachy.backend.headway.domain.dto;

import java.util.List;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class HeadwayCreateCommandDtoBuilder implements Buildable<HeadwayCreateCommandDto> {

  ObjectId ownerId;
  double neckMeasurement;
  double armMeasurement;
  double forearmMeasurement;
  double wristMeasurement;
  double chestMeasurement;
  double waistMeasurement;
  double thighMeasurement;
  double calfMeasurement;
  List<String> images;

  private HeadwayCreateCommandDtoBuilder() {}

  public static HeadwayCreateCommandDtoBuilder create() {
    return new HeadwayCreateCommandDtoBuilder();
  }

  public HeadwayCreateCommandDtoBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withNeckMeasurement(double neckMeasurement) {
    this.neckMeasurement = neckMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withArmMeasurement(double armMeasurement) {
    this.armMeasurement = armMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withForearmMeasurement(double forearmMeasurement) {
    this.forearmMeasurement = forearmMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withWristMeasurement(double wristMeasurement) {
    this.wristMeasurement = wristMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withChestMeasurement(double chestMeasurement) {
    this.chestMeasurement = chestMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withWaistMeasurement(double waistMeasurement) {
    this.waistMeasurement = waistMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withThighMeasurement(double thighMeasurement) {
    this.thighMeasurement = thighMeasurement;
    return this;
  }

  public HeadwayCreateCommandDtoBuilder withCalfMeasurement(double calfMeasurement) {
    this.calfMeasurement = calfMeasurement;
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
