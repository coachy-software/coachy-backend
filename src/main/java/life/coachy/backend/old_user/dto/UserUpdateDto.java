package life.coachy.backend.old_user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import life.coachy.backend.infrastructure.constants.RegexConstants;
import life.coachy.backend.old_user.UserMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;
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

  @Pattern(regexp = RegexConstants.REGEX_NO_SPACE, message = "{pattern}")
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

  @NotNull(message = "{notNull}")
  private ObjectId boardIdentifier;

  UserUpdateDto(UserUpdateDtoBuilder builder) {
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.boardIdentifier = builder.boardIdentifier;
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

  public ObjectId getBoardIdentifier() {
    return this.boardIdentifier;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
