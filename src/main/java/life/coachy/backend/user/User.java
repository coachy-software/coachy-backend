package life.coachy.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import life.coachy.backend.util.IdentifiableEntity;
import org.mongojack.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "coachy")
class User implements IdentifiableEntity<String> {

  @Id
  @ObjectId
  private String identifier;
  private String username;
  private String displayName;
  @JsonIgnore
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;

  User(UserBuilder builder) {
    this.identifier = builder.identifier;
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.accountType = builder.accountType;
    this.roles = builder.roles;
  }

  User() {
  }

  @Override
  public String getIdentifier() {
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
