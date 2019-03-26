package life.coachy.backend.password.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.validation.Match;

@Match(first = "password", second = "confirmPassword")
public class PasswordResetCommandDto {

  @NotNull @NotEmpty private String password;
  private String confirmPassword;

  public String getPassword() {
    return this.password;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

}
