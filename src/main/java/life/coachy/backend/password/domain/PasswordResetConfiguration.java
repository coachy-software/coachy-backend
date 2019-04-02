package life.coachy.backend.password.domain;

import life.coachy.backend.email.EmailFacade;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PasswordResetConfiguration {

  @Bean
  PasswordResetFacade passwordResetFacade(UserFacade userFacade, EmailFacade emailFacade, PasswordResetService service,
      UserQueryRepository userQueryRepository) {
    return new PasswordResetFacade(userFacade, emailFacade, service, userQueryRepository);
  }

}
