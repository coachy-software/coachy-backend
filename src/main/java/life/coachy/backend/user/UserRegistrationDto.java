package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.AbstractDto;
import life.coachy.backend.util.validation.Match;
import life.coachy.backend.util.validation.StringEnumeration;
import org.hibernate.validator.constraints.Length;

@Match.List({
    @Match(first = "password", second = "matchingPassword", message = "Confirm password doesn`t match."),
    @Match(first = "email", second = "matchingEmail", message = "Confirm email doesn`t match.")
})
public class UserRegistrationDto extends AbstractDto<User> {

  @NotEmpty @NotNull @Length(min = 3, max = 32, message = "Username must be at least 3 and up to 32 characters long.")
  private String username;

  @NotEmpty @NotNull @Length(min = 6, message = "Password must be at least 6 characters long.")
  private String password;
  private String matchingPassword;

  @NotEmpty @NotNull @Email(message = "Must be a well-formed email address")
  private String email;
  private String matchingEmail;

  @NotEmpty @NotNull @StringEnumeration(enumClass = AccountType.class, message = "Value doesn't match any account type")
  private String accountType;

  public UserRegistrationDto(String username, String password, String matchingPassword, String email,
      String matchingEmail, String accountType) {
    this.username = username;
    this.password = password;
    this.matchingPassword = matchingPassword;
    this.email = email;
    this.matchingEmail = matchingEmail;
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

  public String getMatchingEmail() {
    return this.matchingEmail;
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
