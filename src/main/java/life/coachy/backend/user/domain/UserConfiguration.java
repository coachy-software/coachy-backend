package life.coachy.backend.user.domain;

import life.coachy.backend.profile.domain.ProfileFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserConfiguration {

  @Bean
  public UserFacade userFacade(UserOperationsService operationsService, UserCrudService crudService, PasswordEncoder passwordEncoder, ProfileFacade profileFacade) {
    UserCreator userCreator = new UserCreator(passwordEncoder);
    return new UserFacade(operationsService, crudService, profileFacade, userCreator);
  }

}
