package life.coachy.backend.user.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.infrastructure.validation.Match;

@Match(first = "password", second = "matchingPassword")
public class UserRegisterCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String email;
  @NotNull @NotEmpty private String username;
  @NotNull @NotEmpty private String password;
  @NotNull @NotEmpty private String matchingPassword;
  @NotNull private AccountTypeDto accountType;

  public String getEmail() {
    return this.email;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getMatchingPassword() {
    return this.matchingPassword;
  }

  public AccountTypeDto getAccountType() {
    return this.accountType;
  }

}
