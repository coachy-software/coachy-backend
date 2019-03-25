package life.coachy.backend.user.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Set;
import org.bson.types.ObjectId;

public class UserDto {

  @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private AccountTypeDto accountType;
  private Set<String> roles;
  private Set<String> permissions;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId boardIdentifier;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getPassword() {
    return this.password;
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

  public Set<String> getRoles() {
    return this.roles;
  }

  public Set<String> getPermissions() {
    return this.permissions;
  }

  public ObjectId getBoardIdentifier() {
    return this.boardIdentifier;
  }

}
