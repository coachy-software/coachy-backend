package life.coachy.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Set;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
class User implements IdentifiableEntity<ObjectId> {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String username;
  private String displayName;
  @JsonIgnore
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;

  User(UserBuilder userBuilder) {
    this.identifier = userBuilder.identifier;
    this.username = userBuilder.username;
    this.displayName = userBuilder.displayName;
    this.password = userBuilder.password;
    this.email = userBuilder.email;
    this.avatar = userBuilder.avatar;
    this.accountType = userBuilder.accountType;
    this.roles = userBuilder.roles;
  }

  User() {
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

  public Set<String> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return "User{" +
        "identifier=" + this.identifier +
        ", username='" + this.username + '\'' +
        ", displayName='" + this.displayName + '\'' +
        ", password='" + this.password + '\'' +
        ", email='" + this.email + '\'' +
        ", avatar='" + this.avatar + '\'' +
        ", accountType=" + this.accountType +
        ", roles=" + this.roles +
        '}';
  }

}
