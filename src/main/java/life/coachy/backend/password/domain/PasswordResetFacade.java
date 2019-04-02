package life.coachy.backend.password.domain;

import life.coachy.backend.email.EmailFacade;
import life.coachy.backend.password.domain.dto.PasswordResetCommandDto;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryRepository;
import net.bytebuddy.utility.RandomString;

public class PasswordResetFacade {

  private UserFacade userFacade;
  private EmailFacade emailFacade;
  private PasswordResetService service;
  private UserQueryRepository userQueryRepository;

  public PasswordResetFacade(UserFacade userFacade, EmailFacade emailFacade, PasswordResetService service, UserQueryRepository userQueryRepository) {
    this.userFacade = userFacade;
    this.emailFacade = emailFacade;
    this.service = service;
    this.userQueryRepository = userQueryRepository;
  }

  public void generateToken(String email) {
    String token = RandomString.make(32);
    this.service.create(email, this.userQueryRepository, token);
    this.emailFacade.sendResetPasswordEmail(email, token);
  }

  public void resetPassword(String token, PasswordResetCommandDto dto) {
    this.service.reset(token, email -> this.userFacade.resetPassword(email, dto.getPassword()));
  }

}
