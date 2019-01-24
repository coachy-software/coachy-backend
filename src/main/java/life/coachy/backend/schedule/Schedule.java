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

// TODO expireAt
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
  private long version; // todo
  private boolean active;
  private List<ScheduleDayDto> trainingDays;

  Schedule(ScheduleBuilder builder) {
    this.identifier = builder.identifier;
    this.name = builder.name;
    this.creator = builder.creator;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.version = builder.version;
    this.active = builder.active;
    this.trainingDays = builder.trainingDays;
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

  public long getVersion() {
    return this.version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public List<ScheduleDayDto> getTrainingDays() {
    return this.trainingDays;
  }

  public void setTrainingDays(List<ScheduleDayDto> trainingDays) {
    this.trainingDays = trainingDays;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

}
