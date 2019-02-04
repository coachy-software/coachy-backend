package life.coachy.backend.user;

import java.util.SortedSet;
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
  private AccountType accountType;
  private SortedSet<String> roles;

  public UserDto(ObjectId identifier, String username, String displayName, String password, String email,
      String avatar, AccountType accountType, SortedSet<String> roles) {
    this.identifier = identifier;
    this.username = username;
    this.displayName = displayName;
    this.password = password;
    this.email = email;
    this.avatar = avatar;
    this.accountType = accountType;
    this.roles = roles;
  }

  public UserDto() {
  }

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
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

  public AccountType getAccountType() {
    return this.accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public SortedSet<String> getRoles() {
    return this.roles;
  }

  public void setRoles(SortedSet<String> roles) {
    this.roles = roles;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

}
