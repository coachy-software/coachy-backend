package life.coachy.backend.infrastructure.query;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class QueryConfiguration {

  @Bean
  QueryOperationsFactory queryOperationsFactory() {
    return new QueryFetchAllOperationsFactory();
  }

}
