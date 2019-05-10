package life.coachy.backend.user.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;
import life.coachy.backend.infrastructure.constant.RegexConstants;
import life.coachy.backend.infrastructure.validation.Match;
import org.hibernate.validator.constraints.Length;

@Match(first = "password", second = "matchingPassword")
public class UserRegisterCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String email;
  @NotNull @NotEmpty @Pattern(regexp = RegexConstants.REGEX_NO_SPACE_AND_SPECIAL_CHARS) @Length(max = 32) private String username;
  @NotNull @NotEmpty @Pattern(regexp = RegexConstants.REGEX_NO_SPACE) @Length(max = 64) private String password;
  @NotNull @NotEmpty private String matchingPassword;
  @NotNull private AccountTypeDto accountType;

  UserRegisterCommandDto() {}

  UserRegisterCommandDto(UserRegisterCommandDtoBuilder builder) {
    this.email = builder.email;
    this.username = builder.username;
    this.password = builder.password;
    this.matchingPassword = builder.matchingPassword;
    this.accountType = builder.accountType;
  }

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
