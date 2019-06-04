package life.coachy.backend.user.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.USERS)
class User {

  @Id ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;
  private Set<String> permissions;
  private ObjectId boardId;

  User() {}

  User(UserBuilder builder) {
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.accountType = builder.accountType;
    this.roles = builder.roles;
    this.permissions = builder.permissions;
  }

  public static UserBuilder builder() {
    return UserBuilder.create();
  }

  void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  void setPassword(String password) {
    this.password = password;
  }

  void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  void setRoles(Set<String> roles) {
    this.roles = roles;
  }

  void setPermissions(Set<String> permissions) {
    this.permissions = permissions;
  }

  void setBoardId(ObjectId boardId) {
    this.boardId = boardId;
  }

}
