package life.coachy.backend.schedule;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonSerialize(using = ScheduleSerializer.class)
@Document("schedules")
class Schedule implements IdentifiableEntity<ObjectId> {

  @Id
  private ObjectId identifier;
  private String name;
//  private UserDto creator;
//  private UserDto charge;
  private String note;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  private boolean active;
  private List<ScheduleDayDto> days;

  Schedule(ScheduleBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
//    this.creator = builder.creator;
//    this.charge = builder.charge;
    this.note = builder.note;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
    this.days = builder.days;
  }

  Schedule() {
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public UserDto getCreator() {
//    return this.creator;
//  }
//
//  public void setCreator(UserDto creator) {
//    this.creator = creator;
//  }
//
//  public UserDto getCharge() {
//    return this.charge;
//  }
//
//  public void setCharge(UserDto charge) {
//    this.charge = charge;
//  }

  public String getNote() {
    return this.note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
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
  public String toString() {
    return "Schedule{" +
        "identifier=" + this.identifier +
        ", name='" + this.name + '\'' +
//        ", creator=" + this.creator +
//        ", charge=" + this.charge +
        ", createdAt=" + this.createdAt +
        ", updatedAt=" + this.updatedAt +
        ", active=" + this.active +
        ", days=" + this.days +
        '}';
  }

}
