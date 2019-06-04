package life.coachy.backend.request.domain;

import life.coachy.backend.request.query.RequestQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RequestConfiguration {

  @Bean
  RequestFacade requestFacade(RequestQueryRepository queryRepository, RequestService requestService, UserFacade userFacade) {
    RequestTokenGenerator tokenGenerator = new RequestTokenGenerator();
    return new RequestFacade(queryRepository, tokenGenerator, requestService, userFacade);
  }

}
