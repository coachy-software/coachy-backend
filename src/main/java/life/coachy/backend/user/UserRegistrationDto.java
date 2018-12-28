package life.coachy.backend.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.util.validation.Match;
import life.coachy.backend.util.validation.StringEnumeration;
import org.hibernate.validator.constraints.Length;

@Match.List({
    @Match(first = "password", second = "matchingPassword", message = "Confirm password doesn`t match."),
    @Match(first = "email", second = "matchingEmail", message = "Confirm email doesn`t match.")
})
public class UserRegistrationDto {

  @JsonProperty("username")
  @NotEmpty @NotNull @Length(min = 3, max = 32, message = "Username must be at least 3 and up to 32 characters long.")
  private String username;

  @JsonProperty("password")
  @NotEmpty @NotNull @Length(min = 6, message = "Password must be at least 6 characters long.")
  private String password;

  @JsonProperty("matchingPassword")
  private String matchingPassword;

  @JsonProperty("email")
  @NotEmpty @NotNull @Email(message = "Must be a well-formed email address")
  private String email;

  @JsonProperty("matchingEmail")
  private String matchingEmail;

  @JsonProperty("accountType")
  @NotEmpty @NotNull @StringEnumeration(enumClass = AccountType.class, message = "Value doesn't match any account type")
  private String accountType;

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

  User toEntity() {
    return new UserBuilder()
        .withUsername(this.username)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAccountType(AccountType.valueOf(this.accountType))
        .build();
  }

}
