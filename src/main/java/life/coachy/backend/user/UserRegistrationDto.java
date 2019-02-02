package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.util.AbstractDto;
import life.coachy.backend.util.validation.Match;
import life.coachy.backend.util.validation.StringEnumeration;
import org.hibernate.validator.constraints.Length;

@Match(first = "password", second = "matchingPassword", message = "{match}")
public class UserRegistrationDto extends AbstractDto<User> {

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE_AND_SPECIAL_CHARS, message = "{pattern}")
  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 3, max = 32, message = "{length}")
  private String username;

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE, message = "{pattern}")
  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 6, message = "{length}")
  private String password;
  private String matchingPassword;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Email(message = "{pattern}")
  private String email;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @StringEnumeration(enumClass = AccountType.class, message = "{match}")
  private String accountType;

  public UserRegistrationDto(String username, String password, String matchingPassword, String email,
      String matchingEmail, String accountType) {
    this.username = username;
    this.password = password;
    this.matchingPassword = matchingPassword;
    this.email = email;
    this.accountType = accountType;
  }

  public UserRegistrationDto() { // JACKSON
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

  public String getEmail() {
    return this.email;
  }

  public AccountType getAccountType() {
    return AccountType.valueOf(this.accountType);
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
