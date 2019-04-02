package life.coachy.backend.schedule.domain;

import java.time.LocalDateTime;
import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import org.bson.types.ObjectId;

final class ScheduleBuilder implements Buildable<Schedule> {

  ObjectId identifier;
  String name;
  ObjectId creator;
  ObjectId charge;
  String note;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  boolean active;
  Set<ScheduleDayDto> days;

  private ScheduleBuilder() {}

  public static ScheduleBuilder create() {
    return new ScheduleBuilder();
  }

  public ScheduleBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ScheduleBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleBuilder withCreator(ObjectId creator) {
    this.creator = creator;
    return this;
  }

  public ScheduleBuilder withCharge(ObjectId charge) {
    this.charge = charge;
    return this;
  }

  public ScheduleBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public ScheduleBuilder withUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public ScheduleBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleBuilder withDays(Set<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public Schedule build() {
    return new Schedule(this);
  }

}
