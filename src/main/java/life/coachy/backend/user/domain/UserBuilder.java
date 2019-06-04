package life.coachy.backend.user.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class UserBuilder implements Buildable<User> {

  String username;
  String displayName;
  String password;
  String email;
  String avatar;
  AccountType accountType;
  Set<String> roles;
  Set<String> permissions;

  private UserBuilder() {}

  public static UserBuilder create() {
    return new UserBuilder();
  }

  UserBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  UserBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  UserBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  UserBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  UserBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  UserBuilder withAccountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  UserBuilder withRoles(Set<String> roles) {
    this.roles = roles;
    return this;
  }

  UserBuilder withPermissions(Set<String> permissions) {
    this.permissions = permissions;
    return this;
  }

  @Override
  public User build() {
    return new User(this);
  }

}
