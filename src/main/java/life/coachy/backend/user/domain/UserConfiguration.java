package life.coachy.backend.user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {

  @Bean
  public UserFacade userFacade(UserOperationsService operationsService, UserCrudService crudService, PasswordEncoder passwordEncoder,
      UserPostRegistrationEventPublisher postRegistrationEventPublisher) {
    UserCreator userCreator = new UserCreator(passwordEncoder);
    return new UserFacade(operationsService, crudService, userCreator, postRegistrationEventPublisher);
  }

}
