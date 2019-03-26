package life.coachy.backend.schedule.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {

  @Bean
  ScheduleFacade scheduleFacade(ScheduleService service) {
    ScheduleCreator creator = new ScheduleCreator();
    return new ScheduleFacade(service, creator);
  }

}
