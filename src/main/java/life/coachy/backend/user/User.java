package life.coachy.backend.user;

import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
class User implements IdentifiableEntity<ObjectId> {

  @Id
  private final ObjectId identifier;
  private String username;
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;

  User(UserBuilder userBuilder) {
    this.identifier = userBuilder.identifier;
    this.username = userBuilder.username;
    this.password = userBuilder.password;
    this.email = userBuilder.email;
    this.avatar = userBuilder.avatar;
    this.accountType = userBuilder.accountType;
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public AccountType getAccountType() {
    return this.accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
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

}
