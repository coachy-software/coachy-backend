package life.coachy.backend.schedule.dto;

import java.util.Date;
import java.util.List;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public final class ScheduleDtoBuilder implements Buildable<ScheduleDto> {

  ObjectId identifier;
  String name;
  UserDto creator;
  Date createdAt;
  Date updatedAt;
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

  public ScheduleDtoBuilder withCreator(UserDto creator) {
    this.creator = creator;
    return this;
  }

  public ScheduleDtoBuilder withCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public ScheduleDtoBuilder withUpdatedAt(Date updatedAt) {
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
