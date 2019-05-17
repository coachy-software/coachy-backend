package life.coachy.backend.headway.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

class Headway {

  @Id private ObjectId identifier;
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
  @CreatedDate private LocalDateTime createdAt;

  Headway() {}

  Headway(HeadwayBuilder builder) {
    this.identifier = builder.identifier;
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
    this.createdAt = builder.createdAt;
  }

}
