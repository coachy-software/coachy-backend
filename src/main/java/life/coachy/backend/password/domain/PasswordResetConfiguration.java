package life.coachy.backend.password.domain;

import life.coachy.backend.email.EmailFacade;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PasswordResetConfiguration {

  @Bean
  PasswordResetFacade passwordResetFacade(UserFacade userFacade, EmailFacade emailFacade, PasswordResetService service,
      UserQueryDtoRepository userQueryDtoRepository) {
    return new PasswordResetFacade(userFacade, emailFacade, service, userQueryDtoRepository);
  }

}
