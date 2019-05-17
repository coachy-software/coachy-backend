package life.coachy.backend.headway.domain;

import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HeadwayConfiguration {

  @Bean
  HeadwayFacade headwayFacade(HeadwayService service, UserFacade userFacade) {
    HeadwayCreator headwayCreator = new HeadwayCreator();
    return new HeadwayFacade(service, headwayCreator, userFacade);
  }

}
