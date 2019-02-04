package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import life.coachy.backend.util.validation.Match;
import life.coachy.backend.util.validation.StringEnumeration;
import org.hibernate.validator.constraints.Length;

@DataTransferObject(mapperClass = UserMapper.class, entityName = "User")
@Match(first = "password", second = "matchingPassword", message = "{match}")
public class UserRegistrationDto extends AbstractDto {

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

  public UserRegistrationDto(String username, String password, String matchingPassword, String email, String accountType) {
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

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchingPassword() {
    return this.matchingPassword;
  }

  public void setMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAccountType() {
    return this.accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
