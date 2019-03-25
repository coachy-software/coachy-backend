package life.coachy.backend.old_schedule.day;

import life.coachy.backend.old_schedule.day.dto.ScheduleDayDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleDayMapper {

  ScheduleDayMapper INSTANCE = Mappers.getMapper(ScheduleDayMapper.class);

  ScheduleDay scheduleDayDtoToScheduleDay(ScheduleDayDto dto);

}
