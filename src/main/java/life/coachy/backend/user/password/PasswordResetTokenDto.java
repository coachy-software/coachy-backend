package life.coachy.backend.user.password;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.validation.Match;

@Match(first = "password", second = "confirmPassword", message = "{match}")
public class PasswordResetTokenDto extends AbstractDto {

  @NotNull(message = "{notNull}")
  @NotEmpty(message = "{notEmpty}")
  private String password;
  private String confirmPassword;

  PasswordResetTokenDto(String password, String confirmPassword) {
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  PasswordResetTokenDto() {}

  public String getPassword() {
    return this.password;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

}
