package life.coachy.backend.schedule;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.util.List;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.user.UserDto;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

class Schedule implements IdentifiableEntity<ObjectId> {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private UserDto creator;
  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updatedAt;
  private boolean active;
  private List<ScheduleDayDto> days;

  Schedule(ScheduleBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.creator = builder.creator;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
    this.days = builder.days;
  }

  Schedule() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UserDto getCreator() {
    return this.creator;
  }

  public void setCreator(UserDto creator) {
    this.creator = creator;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<ScheduleDayDto> getDays() {
    return this.days;
  }

  public void setDays(List<ScheduleDayDto> days) {
    this.days = days;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

}
