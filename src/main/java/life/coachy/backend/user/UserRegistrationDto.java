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

@Match(first = "password", second = "matchingPassword", message = "{match.password}")
public class UserRegistrationDto extends AbstractDto<User> {

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE_AND_SPECIAL_CHARS, message = "{username.pattern}")
  @NotEmpty(message = "{username.notEmpty}") @NotNull(message = "{username.notNull}") @Length(min = 3, max = 32, message = "{username.length}")
  private String username;

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE, message = "{password.pattern}")
  @NotEmpty(message = "{password.notEmpty}") @NotNull(message = "{password.notNull}") @Length(min = 6, message = "{password.length}")
  private String password;
  private String matchingPassword;

  @NotEmpty(message = "{email.notEmpty}") @NotNull(message = "{email.notNull}") @Email(message = "{email.format}")
  private String email;

  @NotEmpty(message = "{accountType.notEmpty}") @NotNull(message = "{accountType.notNull}") @StringEnumeration(enumClass = AccountType.class, message = "{match.accountType}")
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
  public User toEntity() {
    return new UserBuilder()
        .withUsername(this.username)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAccountType(AccountType.valueOf(this.accountType))
        .build();
  }

}
