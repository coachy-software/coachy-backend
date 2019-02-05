package life.coachy.backend.schedule;

import life.coachy.backend.schedule.dto.ScheduleDto;
import life.coachy.backend.schedule.dto.ScheduleUpdateDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper extends MapStructMapper {

  ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

  @Mapping(target = "identifier", source = "dto.identifier")
  Schedule scheduleDtoToSchedule(ScheduleDto dto);

  Schedule scheduleUpdateDtoToSchedule(ScheduleUpdateDto dto);

}
