package life.coachy.backend.schedule;

import java.util.Date;
import java.util.List;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

class ScheduleBuilder implements Buildable<Schedule> {

  ObjectId identifier;
  String name;
  UserDto creator;
  UserDto charge;
  Date createdAt;
  Date updatedAt;
  boolean active;
  List<ScheduleDayDto> days;

  private ScheduleBuilder() {}

  public static ScheduleBuilder createBuilder() {
    return new ScheduleBuilder();
  }

  ScheduleBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  ScheduleBuilder withName(String name) {
    this.name = name;
    return this;
  }

  ScheduleBuilder withCreator(UserDto creator) {
    this.creator = creator;
    return this;
  }

  ScheduleBuilder withCharge(UserDto charge) {
    this.charge = charge;
    return this;
  }

  ScheduleBuilder withCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  ScheduleBuilder withUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  ScheduleBuilder isActive(boolean active) {
    this.active = active;
    return this;
  }

  ScheduleBuilder withDays(List<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public Schedule build() {
    return new Schedule(this);
  }

}
