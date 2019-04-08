package life.coachy.backend.schedule.domain;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.SCHEDULES)
class Schedule {

  @Id ObjectId identifier;
  private String name;
  private ObjectId creator;
  private ObjectId charge;
  private String note;
  @CreatedDate private LocalDateTime createdAt;
  @LastModifiedDate private LocalDateTime updatedAt;
  private boolean active;
  private LinkedHashSet<ScheduleDayDto> days;

  Schedule() {}

  Schedule(ScheduleBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.creator = builder.creator;
    this.charge = builder.charge;
    this.note = builder.note;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
    this.days = builder.days;
  }

  public static ScheduleBuilder builder() {
    return ScheduleBuilder.create();
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

  public void setNote(String note) {
    this.note = note;
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

  public void setDays(LinkedHashSet<ScheduleDayDto> days) {
    this.days = days;
  }

}
