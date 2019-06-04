package life.coachy.backend.schedule.domain.dto;

public class ScheduleAcceptCommandDto {

  private boolean active;

  ScheduleAcceptCommandDto() {}

  ScheduleAcceptCommandDto(boolean active) {
    this.active = active;
  }

  public boolean isActive() {
    return this.active;
  }

}
