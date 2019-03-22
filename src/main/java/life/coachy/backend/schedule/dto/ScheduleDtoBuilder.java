package life.coachy.backend.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

public final class ScheduleDtoBuilder implements Buildable<ScheduleDto> {

  ObjectId identifier;
  String name;
//  UserDto creator;
//  UserDto charge;
  String note;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  boolean active;
  List<ScheduleDayDto> days;

  private ScheduleDtoBuilder() {}

  public static ScheduleDtoBuilder createBuilder() {
    return new ScheduleDtoBuilder();
  }

  public ScheduleDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public ScheduleDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

//  public ScheduleDtoBuilder withCreator(UserDto creator) {
//    this.creator = creator;
//    return this;
//  }
//
//  public ScheduleDtoBuilder withCharge(UserDto charge) {
//    this.charge = charge;
//    return this;
//  }

  // todo

  public ScheduleDtoBuilder withNote(String note) {
    this.note = note;
    return this;
  }

  public ScheduleDtoBuilder withCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public ScheduleDtoBuilder withUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  public ScheduleDtoBuilder withActive(boolean active) {
    this.active = active;
    return this;
  }

  public ScheduleDtoBuilder withDays(List<ScheduleDayDto> days) {
    this.days = days;
    return this;
  }

  @Override
  public ScheduleDto build() {
    return new ScheduleDto(this);
  }

}
