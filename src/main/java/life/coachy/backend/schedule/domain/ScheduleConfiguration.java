package life.coachy.backend.schedule.domain;

import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.schedule.query.ScheduleQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ScheduleConfiguration {

  @Bean
  ScheduleFacade scheduleFacade(ScheduleQueryRepository queryDtoRepository, QueryOperationsFactory queryOperationsFactory, ScheduleService service,
      UserFacade userFacade) {
    ScheduleCreator creator = new ScheduleCreator();
    return new ScheduleFacade(queryDtoRepository, queryOperationsFactory, service, creator, userFacade);
  }

}
