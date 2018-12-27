package life.coachy.backend.user;

import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

class UserBuilder implements Buildable<User> {

  ObjectId identifier;
  String username;
  String password;
  String email;
  String avatar;
  AccountType accountType;

  public UserBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  public UserBuilder withUsername(String username) {
    this.username = username;
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

  @Override
  public User build() {
    return new User(this);
  }

}
