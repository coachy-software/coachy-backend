package life.coachy.backend.user.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.USERS)
class User {

  @Id
  private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountType accountType;
  private Set<String> roles;
  private Set<String> permissions;
  private ObjectId boardIdentifier;

  User(UserBuilder builder) {
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

  User() {}

  public static UserBuilder builder() {
    return UserBuilder.create();
  }

}