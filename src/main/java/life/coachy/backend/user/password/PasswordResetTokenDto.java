package life.coachy.backend.user.password;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;
import life.coachy.backend.util.validation.Match;

@Match(first = "password", second = "confirmPassword", message = "{match.password}")
public class PasswordResetTokenDto extends AbstractDto<PasswordResetToken> {

  @NotNull(message = "{password.notNull}") @NotEmpty(message = "{password.notEmpty}")
  private String password;
  private String confirmPassword;

  PasswordResetTokenDto(String password, String confirmPassword) {
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public PasswordResetTokenDto() { // JACKSON
  }

  public String getPassword() {
    return this.password;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

}
