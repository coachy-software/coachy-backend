package life.coachy.backend.user.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Set;
import life.coachy.backend.infrastructure.constants.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import life.coachy.backend.user.domain.dto.AccountTypeDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.USERS)
public class UserQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String username;
  private String displayName;
  private String email;
  private String avatar;
  private AccountTypeDto accountType;
  private Set<String> permissions;
  private ObjectId boardIdentifier;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getAvatar() {
    return this.avatar;
  }

  public AccountTypeDto getAccountType() {
    return this.accountType;
  }

  public Set<String> getPermissions() {
    return this.permissions;
  }

  public ObjectId getBoardIdentifier() {
    return this.boardIdentifier;
  }

}
