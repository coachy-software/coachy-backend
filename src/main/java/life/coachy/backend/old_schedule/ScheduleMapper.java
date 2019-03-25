package life.coachy.backend.old_schedule;

import java.util.List;
import life.coachy.backend.old_schedule.dto.ScheduleDto;
import life.coachy.backend.old_schedule.dto.ScheduleGlobalDto;
import life.coachy.backend.old_schedule.dto.ScheduleUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {

  ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

//  @Mapping(target = "identifier", source = "dto.identifier")
  Schedule scheduleDtoToSchedule(ScheduleDto dto);

  Schedule scheduleUpdateDtoToSchedule(ScheduleUpdateDto dto);

//  @Mapping(target = "creator", source = "old_schedule.creator.identifier")
//  @Mapping(target = "charge", source = "old_schedule.charge.identifier")
  ScheduleGlobalDto scheduleToScheduleGlobalDto(Schedule schedule);

  Schedule scheduleGlobalDtoToSchedule(ScheduleGlobalDto dto);

  List<ScheduleGlobalDto> schedulesToScheduleGlobalDtos(List<Schedule> schedules);

  default Page<ScheduleGlobalDto> schedulesToScheduleGlobalDtos(Page<Schedule> schedules) {
    return schedules.map(this::scheduleToScheduleGlobalDto);
  }

}
