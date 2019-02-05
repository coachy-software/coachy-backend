package life.coachy.backend.schedule.day;

import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleDayMapper extends MapStructMapper {

  ScheduleDayMapper INSTANCE = Mappers.getMapper(ScheduleDayMapper.class);

  ScheduleDay scheduleDayDtoToScheduleDay(ScheduleDayDto dto);

}
