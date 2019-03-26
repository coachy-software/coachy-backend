package life.coachy.backend.schedule.domain;

import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;

class ScheduleCreator {

  Schedule from(ScheduleCreateCommandDto dto) {
    return Schedule.builder()
        .withName(dto.getName())
        .withCreator(dto.getCreator())
        .withCharge(dto.getCharge())
        .withNote(dto.getNote())
        .withActive(dto.isActive())
        .withDays(dto.getDays())
        .build();
  }

}
