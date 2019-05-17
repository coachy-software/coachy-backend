package life.coachy.backend.headway.domain.dto;

import java.util.List;
import org.bson.types.ObjectId;

public class HeadwayCreateCommandDto {

  private ObjectId ownerId;
  private double neckMeasurement;
  private double armMeasurement;
  private double forearmMeasurement;
  private double wristMeasurement;
  private double chestMeasurement;
  private double waistMeasurement;
  private double thighMeasurement;
  private double calfMeasurement;
  private List<String> images;

  HeadwayCreateCommandDto() {}

  HeadwayCreateCommandDto(HeadwayCreateCommandDtoBuilder builder) {
    this.ownerId = builder.ownerId;
    this.neckMeasurement = builder.neckMeasurement;
    this.armMeasurement = builder.armMeasurement;
    this.forearmMeasurement = builder.forearmMeasurement;
    this.wristMeasurement = builder.wristMeasurement;
    this.chestMeasurement = builder.chestMeasurement;
    this.waistMeasurement = builder.waistMeasurement;
    this.thighMeasurement = builder.thighMeasurement;
    this.calfMeasurement = builder.calfMeasurement;
    this.images = builder.images;
  }

  public ObjectId getOwnerId() {
    return this.ownerId;
  }

  public double getNeckMeasurement() {
    return this.neckMeasurement;
  }

  public double getArmMeasurement() {
    return this.armMeasurement;
  }

  public double getForearmMeasurement() {
    return this.forearmMeasurement;
  }

  public double getWristMeasurement() {
    return this.wristMeasurement;
  }

  public double getChestMeasurement() {
    return this.chestMeasurement;
  }

  public double getWaistMeasurement() {
    return this.waistMeasurement;
  }

  public double getThighMeasurement() {
    return this.thighMeasurement;
  }

  public double getCalfMeasurement() {
    return this.calfMeasurement;
  }

  public List<String> getImages() {
    return this.images;
  }

}
