package life.coachy.backend.user.dto;

import java.util.Set;
import java.util.SortedSet;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public final class UserDtoBuilder implements Buildable<UserDto> {

  ObjectId identifier;
  String username;
  String displayName;
  String password;
  String email;
  String avatar;
  String accountType;
  SortedSet<String> roles;
  Set<String> permissions;

  private UserDtoBuilder() {}

  public static UserDtoBuilder createBuilder() {
    return new UserDtoBuilder();
  }

  public UserDtoBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public UserDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserDtoBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public UserDtoBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserDtoBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserDtoBuilder withAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  public UserDtoBuilder withRoles(SortedSet<String> roles) {
    this.roles = roles;
    return this;
  }

  public UserDtoBuilder withPermissions(Set<String> permissions) {
    this.permissions = permissions;
    return this;
  }

  @Override
  public UserDto build() {
    return new UserDto(this);
  }

}
