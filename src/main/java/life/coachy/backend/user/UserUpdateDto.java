package life.coachy.backend.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.BackendConstants;
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

  public UserUpdateDto(String username, String displayName, String password, String email, String avatar) {
    this.username = username;
    this.displayName = displayName;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
  }

  public UserUpdateDto() {
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
