package life.coachy.backend.schedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import life.coachy.backend.schedule.ScheduleMapper;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = ScheduleMapper.class, entityName = "Schedule")
public class ScheduleGlobalDto extends AbstractDto {

  private String name;
  @JsonIgnoreProperties({"username", "displayName", "password", "email", "avatar", "accountType", "roles"}) // todo
  private UserDto creator;
  @JsonIgnoreProperties({"username", "displayName", "password", "email", "avatar", "accountType", "roles"})
  private UserDto charge;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private boolean active;

  ScheduleGlobalDto(ScheduleGlobalDtoBuilder builder) {
    this.name = builder.name;
    this.creator = builder.creator;
    this.charge = builder.charge;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.active = builder.active;
  }

  public ScheduleGlobalDto() {}

  public String getName() {
    return this.name;
  }

  public UserDto getCreator() {
    return this.creator;
  }

  public UserDto getCharge() {
    return this.charge;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public boolean isActive() {
    return this.active;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCreator(UserDto creator) {
    this.creator = creator;
  }

  public void setCharge(UserDto charge) {
    this.charge = charge;
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

  @Override
  public String getEntityName() {
    return this.name;
  }

}
