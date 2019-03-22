package life.coachy.backend.password.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.validation.Match;

@Match(first = "password", second = "confirmPassword")
public class PasswordResetCommandDto extends AbstractDto {

  @NotNull @NotEmpty private String password;
  private String confirmPassword;

  public String getPassword() {
    return this.password;
  }

  public String getConfirmPassword() {
    return this.confirmPassword;
  }

}
