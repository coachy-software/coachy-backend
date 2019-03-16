package life.coachy.backend.user;

import java.util.Set;
import java.util.SortedSet;
import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

final class UserBuilder implements Buildable<User> {

  ObjectId identifier;
  String username;
  String displayName;
  String password;
  String email;
  String avatar;
  AccountType accountType;
  SortedSet<String> roles;
  Set<String> permissions;
  ObjectId boardIdentifier;

  private UserBuilder() {}

  public static UserBuilder createBuilder() { return new UserBuilder(); }

  public UserBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public UserBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public UserBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserBuilder withAccountType(AccountType accountType) {
    this.accountType = accountType;
    return this;
  }

  public UserBuilder withRoles(SortedSet<String> roles) {
    this.roles = roles;
    return this;
  }

  public UserBuilder withPermissions(Set<String> permissions) {
    this.permissions = permissions;
    return this;
  }

  public UserBuilder withBoardIdentifier(ObjectId boardIdentifier) {
    this.boardIdentifier = boardIdentifier;
    return this;
  }

  @Override
  public User build() {
    return new User(this);
  }

}
