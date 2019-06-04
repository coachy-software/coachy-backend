package life.coachy.backend.schedule.domain;

import com.google.common.collect.Sets;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;

class ScheduleCreator {

  Schedule from(ScheduleCreateCommandDto dto) {
    return Schedule.builder()
        .withName(dto.getName())
        .withCreator(dto.getCreator())
        .withCharge(dto.getCharge())
        .withNote(dto.getNote())
        .withActive(dto.isActive())
        .withDays(dto.getDays())
        .withAccepted(false)
        .build();
  }

  Schedule from(ScheduleQueryDto dto) {
    return Schedule.builder()
        .withName(dto.getName())
        .withCreator(dto.getCreator())
        .withCharge(dto.getCharge())
        .withNote(dto.getNote())
        .withActive(dto.isActive())
        .withDays(Sets.newLinkedHashSet(dto.getDays()))
        .withAccepted(dto.isAccepted())
        .build();
  }

  Schedule from(ScheduleUpdateCommandDto dto) {
    return Schedule.builder()
        .withName(dto.getName())
        .withActive(dto.isActive())
        .withNote(dto.getNote())
        .withDays(dto.getDays())
        .build();
  }

}
