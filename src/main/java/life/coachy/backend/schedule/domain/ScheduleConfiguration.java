package life.coachy.backend.schedule.domain;

import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {

  @Bean
  ScheduleFacade scheduleFacade(ScheduleService service, UserFacade userFacade) {
    ScheduleCreator creator = new ScheduleCreator();
    return new ScheduleFacade(service, creator, userFacade);
  }

}
