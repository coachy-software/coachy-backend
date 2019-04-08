package life.coachy.backend.schedule.domain;

import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;

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

  Schedule from(ScheduleUpdateEntireEntityCommandDto dto) {
    return Schedule.builder()
        .withName(dto.getName())
        .withActive(dto.isActive())
        .withNote(dto.getNote())
        .withDays(dto.getDays())
        .build();
  }

}
