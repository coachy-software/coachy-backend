package life.coachy.backend.user.domain.dto;

import java.util.Set;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class UserUpdateEntireEntityCommandDto implements CommandDtoMarker {

  private String username;
  private String displayName;
  private String password;
  private String email;
  private String avatar;
  private Set<String> permissions;

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

  public Set<String> getPermissions() {
    return this.permissions;
  }

}
