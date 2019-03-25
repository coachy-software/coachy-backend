package life.coachy.backend.old_schedule.dto;

import life.coachy.backend.old_schedule.ScheduleMapper;
import life.coachy.backend.infrastructure.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDtoMapperFactory extends AbstractDtoMapperFactory<ScheduleMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.old_schedule.dto");

  public ScheduleDtoMapperFactory() {
    super(ScheduleMapper.INSTANCE, REFLECTIONS);
  }

}
