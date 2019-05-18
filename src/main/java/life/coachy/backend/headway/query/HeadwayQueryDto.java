package life.coachy.backend.headway.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.HEADWAYS)
public class HeadwayQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId ownerId;
  private double neckMeasurement;
  private double armMeasurement;
  private double forearmMeasurement;
  private double wristMeasurement;
  private double chestMeasurement;
  private double waistMeasurement;
  private double thighMeasurement;
  private double calfMeasurement;
  private List<String> images;
  @JsonSerialize(using = ToStringSerializer.class) @CreatedDate private LocalDateTime createdAt;

  public ObjectId getIdentifier() {
    return this.identifier;
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

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

}
