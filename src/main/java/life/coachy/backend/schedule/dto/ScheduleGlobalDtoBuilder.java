package life.coachy.backend.schedule.dto;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

public final class ScheduleGlobalDtoBuilder {

  String name;
  ObjectId creator;
  ObjectId charge;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  boolean active;

  private ScheduleGlobalDtoBuilder() {}

  public static ScheduleGlobalDtoBuilder createBuilder() {
    return new ScheduleGlobalDtoBuilder();
  }

  public ScheduleGlobalDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleGlobalDtoBuilder withCreator(ObjectId creator) {
    this.creator = creator;
    return this;
  }

  public ScheduleGlobalDtoBuilder withCharge(ObjectId charge) {
    this.charge = charge;
    return this;
  }

  public ScheduleGlobalDtoBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public ScheduleGlobalDtoBuilder withUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public ScheduleGlobalDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleGlobalDto build() {
    return new ScheduleGlobalDto(this);
  }

}
