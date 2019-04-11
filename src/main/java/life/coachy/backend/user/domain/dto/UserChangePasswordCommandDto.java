package life.coachy.backend.user.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.infrastructure.constants.RegexConstants;
import life.coachy.backend.infrastructure.validation.Match;
import org.hibernate.validator.constraints.Length;

@Match(first = "newPassword", second = "confirmNewPassword")
public class UserChangePasswordCommandDto {

  @NotNull @NotEmpty private String oldPassword;
  @NotNull @NotEmpty @Pattern(regexp = RegexConstants.REGEX_NO_SPACE) @Length(max = 64) private String newPassword;
  @NotNull @NotEmpty private String confirmNewPassword;

  UserChangePasswordCommandDto() {}

  public UserChangePasswordCommandDto(String oldPassword, String newPassword, String confirmNewPassword) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
    this.confirmNewPassword = confirmNewPassword;
  }

  public String getOldPassword() {
    return this.oldPassword;
  }

  public String getNewPassword() {
    return this.newPassword;
  }

  public String getConfirmNewPassword() {
    return this.confirmNewPassword;
  }

}
