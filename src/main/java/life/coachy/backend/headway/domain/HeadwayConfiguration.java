package life.coachy.backend.headway.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HeadwayConfiguration {

  @Bean
  HeadwayFacade headwayFacade(HeadwayService service) {
    HeadwayCreator headwayCreator = new HeadwayCreator();
    return new HeadwayFacade(service, headwayCreator);
  }

}
