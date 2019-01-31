package life.coachy.backend.user;

import java.util.Set;
import life.coachy.backend.util.AbstractDto;
import org.bson.types.ObjectId;

public class UserDto extends AbstractDto<User> {

  private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;

  public UserDto(ObjectId identifier, String username, String displayName, String password, String email,
      String avatar, AccountType accountType, Set<String> roles) {
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

  public AccountType getAccountType() {
    return this.accountType;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  @Override
  public String getEntityName() {
    return this.username;
  }

  @Override
  public User toEntity() {
    return new UserBuilder()
        .withIdentifier(this.identifier)
        .withUsername(this.username)
        .withDisplayName(this.displayName)
        .withPassword(this.password)
        .withEmail(this.email)
        .withAvatar(this.avatar)
        .withAccountType(this.accountType)
        .withRoles(this.roles)
        .build();
  }

}
