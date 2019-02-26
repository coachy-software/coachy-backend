package life.coachy.backend.user.dto;

import java.util.SortedSet;
import life.coachy.backend.user.UserMapper;
import life.coachy.backend.util.dto.AbstractDto;
import life.coachy.backend.util.dto.DataTransferObject;
import org.bson.types.ObjectId;

@DataTransferObject(mapperClass = UserMapper.class, entityName = "User")
public class UserDto extends AbstractDto {

  private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private String accountType;
  private SortedSet<String> roles;

  UserDto(UserDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.accountType = builder.accountType;
    this.roles = builder.roles;
  }

  public UserDto() {}

  public ObjectId getIdentifier() {
    return this.identifier;
  }

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

  public String getAccountType() {
    return this.accountType;
  }

  public SortedSet<String> getRoles() {
    return this.roles;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public void setRoles(SortedSet<String> roles) {
    this.roles = roles;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
