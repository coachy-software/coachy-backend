package life.coachy.backend.schedule.domain.dto;

import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import org.bson.types.ObjectId;

public final class ScheduleCreateCommandDtoBuilder implements Buildable<ScheduleCreateCommandDto> {

  String name;
  ObjectId creator;
  ObjectId charge;
  String note;
  boolean active;
  Set<ScheduleDayDto> days;

  private ScheduleCreateCommandDtoBuilder() {}

  public static ScheduleCreateCommandDtoBuilder create() {
    return new ScheduleCreateCommandDtoBuilder();
  }

  public ScheduleCreateCommandDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleCreateCommandDtoBuilder withCreator(ObjectId creator) {
    this.creator = creator;
    return this;
  }

  public ScheduleCreateCommandDtoBuilder withCharge(ObjectId charge) {
    this.charge = charge;
    return this;
  }

  public ScheduleCreateCommandDtoBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleCreateCommandDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleCreateCommandDtoBuilder withDays(Set<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public ScheduleCreateCommandDto build() {
    return new ScheduleCreateCommandDto(this);
  }

}
