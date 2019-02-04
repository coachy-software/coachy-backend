package life.coachy.backend.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
import life.coachy.backend.user.UserMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@DataTransferObject(mapperClass = UserMapper.class, entityName = "User")
public class UserUpdateDto extends AbstractDto {

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 3, max = 32, message = "{length}")
  private String username;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 3, max = 32, message = "{length}")
  private String displayName;

  @Pattern(regexp = BackendConstants.REGEX_NO_SPACE, message = "{pattern}")
  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Length(min = 6, message = "{length}")
  private String password;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @Email(message = "{pattern}")
  private String email;

  @NotEmpty(message = "{notEmpty}")
  @NotNull(message = "{notNull}")
  @URL(message = "{pattern}")
  private String avatar;

  UserUpdateDto(UserUpdateDtoBuilder builder) {
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
  }

  UserUpdateDto() {}

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getPassword() {
    return this.password;
  }

  public String getEmail() {
    return this.email;
  }

  public String getAvatar() {
    return this.avatar;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
