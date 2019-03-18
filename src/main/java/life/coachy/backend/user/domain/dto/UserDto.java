package life.coachy.backend.user.domain.dto;

import java.util.Set;
import org.bson.types.ObjectId;

public class UserDto {

  private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountTypeDto accountType;
  private Set<String> roles;
  private Set<String> permissions;
  private ObjectId boardIdentifier;

  protected UserDto(UserDtoBuilder builder) {
    this.identifier = builder.identifier;
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.accountType = builder.accountType;
    this.roles = builder.roles;
    this.permissions = builder.permissions;
    this.boardIdentifier = builder.boardIdentifier;
  }

  public static UserDtoBuilder create() {
    return UserDtoBuilder.create();
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

  public AccountTypeDto getAccountType() {
    return this.accountType;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  public Set<String> getPermissions() {
    return this.permissions;
  }

  public ObjectId getBoardIdentifier() {
    return this.boardIdentifier;
  }

}
