package life.coachy.backend.schedule;

import java.util.List;
import life.coachy.backend.schedule.dto.ScheduleDto;
import life.coachy.backend.schedule.dto.ScheduleGlobalDto;
import life.coachy.backend.schedule.dto.ScheduleUpdateDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper extends MapStructMapper {

  ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

  @Mapping(target = "identifier", source = "dto.identifier")
  Schedule scheduleDtoToSchedule(ScheduleDto dto);

  Schedule scheduleUpdateDtoToSchedule(ScheduleUpdateDto dto);

  @Mapping(target = "charge", source = "schedule.charge.identifier")
  @Mapping(target = "creator", source = "schedule.creator.identifier")
  ScheduleGlobalDto scheduleToScheduleGlobalDto(Schedule schedule);

  List<ScheduleGlobalDto> schedulesToScheduleGlobalDtos(List<Schedule> schedules);

  default Page<ScheduleGlobalDto> schedulesToScheduleGlobalDtos(Page<Schedule> schedules) {
    return schedules.map(this::scheduleToScheduleGlobalDto);
  }

}
