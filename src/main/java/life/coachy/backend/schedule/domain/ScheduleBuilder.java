package life.coachy.backend.schedule.domain;

import java.util.LinkedHashSet;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import org.bson.types.ObjectId;

final class ScheduleBuilder implements Buildable<Schedule> {

  String name;
  ObjectId creator;
  ObjectId charge;
  String note;
  boolean active;
  LinkedHashSet<ScheduleDayDto> days;
  boolean accepted;

  private ScheduleBuilder() {}

  public static ScheduleBuilder create() {
    return new ScheduleBuilder();
  }

  ScheduleBuilder withName(String name) {
    this.name = name;
    return this;
  }

  ScheduleBuilder withCreator(ObjectId creator) {
    this.creator = creator;
    return this;
  }

  ScheduleBuilder withCharge(ObjectId charge) {
    this.charge = charge;
    return this;
  }

  ScheduleBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  ScheduleBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  ScheduleBuilder withDays(LinkedHashSet<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  ScheduleBuilder withAccepted(boolean accepted) {
    this.accepted = accepted;
    return this;
  }

  @Override
  public Schedule build() {
    return new Schedule(this);
  }

}
