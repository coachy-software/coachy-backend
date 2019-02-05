package life.coachy.backend.schedule.dto;

import life.coachy.backend.schedule.ScheduleMapper;
import life.coachy.backend.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDtoMapperFactory extends AbstractDtoMapperFactory<ScheduleMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.schedule.dto");

  public ScheduleDtoMapperFactory() {
    super(ScheduleMapper.INSTANCE, REFLECTIONS);
  }

}
