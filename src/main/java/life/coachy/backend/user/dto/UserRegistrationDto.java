package life.coachy.backend.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.user.UserMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import life.coachy.backend.util.validation.Match;
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
  private String accountType;

  UserRegistrationDto(UserRegistrationDtoBuilder builder) {
    this.username = builder.username;
    this.password = builder.password;
    this.matchingPassword = builder.matchingPassword;
    this.email = builder.email;
    this.accountType = builder.accountType;
  }

  UserRegistrationDto() {}

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

  public String getAccountType() {
    return this.accountType;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
