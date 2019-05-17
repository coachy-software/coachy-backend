package life.coachy.backend.headway.domain;

import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class HeadwayBuilder implements Buildable<Headway> {

  ObjectId identifier;
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
  LocalDateTime createdAt;

  private HeadwayBuilder() {}

  public static HeadwayBuilder create() {
    return new HeadwayBuilder();
  }

  public HeadwayBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public HeadwayBuilder withOwnerId(ObjectId ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  public HeadwayBuilder withNeckMeasurement(double neckMeasurement) {
    this.neckMeasurement = neckMeasurement;
    return this;
  }

  public HeadwayBuilder withArmMeasurement(double armMeasurement) {
    this.armMeasurement = armMeasurement;
    return this;
  }

  public HeadwayBuilder withForearmMeasurement(double forearmMeasurement) {
    this.forearmMeasurement = forearmMeasurement;
    return this;
  }

  public HeadwayBuilder withWristMeasurement(double wristMeasurement) {
    this.wristMeasurement = wristMeasurement;
    return this;
  }

  public HeadwayBuilder withChestMeasurement(double chestMeasurement) {
    this.chestMeasurement = chestMeasurement;
    return this;
  }

  public HeadwayBuilder withWaistMeasurement(double waistMeasurement) {
    this.waistMeasurement = waistMeasurement;
    return this;
  }

  public HeadwayBuilder withThighMeasurement(double thighMeasurement) {
    this.thighMeasurement = thighMeasurement;
    return this;
  }

  public HeadwayBuilder withCalfMeasurement(double calfMeasurement) {
    this.calfMeasurement = calfMeasurement;
    return this;
  }

  public HeadwayBuilder withImages(List<String> images) {
    this.images = images;
    return this;
  }

  public HeadwayBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public Headway build() {
    return new Headway(this);
  }

}
