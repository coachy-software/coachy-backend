package life.coachy.backend.schedule.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import life.coachy.backend.schedule.ScheduleMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = ScheduleMapper.class, entityName = "Schedule")
public class ScheduleGlobalDto extends AbstractDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String name;
  private ObjectId creator;
  private ObjectId charge;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean active;

  ScheduleGlobalDto(ScheduleGlobalDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.creator = builder.creator;
    this.charge = builder.charge;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
  }

  public ScheduleGlobalDto() {}

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public ObjectId getCreator() {
    return this.creator;
  }

  public ObjectId getCharge() {
    return this.charge;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCreator(ObjectId creator) {
    this.creator = creator;
  }

  public void setCharge(ObjectId charge) {
    this.charge = charge;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
