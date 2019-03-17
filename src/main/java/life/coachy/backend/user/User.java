package life.coachy.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Set;
import java.util.SortedSet;
import life.coachy.backend.old_user.UserBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
class User {

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
  private SortedSet<String> roles;
  private Set<String> permissions;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId boardIdentifier;

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

}
