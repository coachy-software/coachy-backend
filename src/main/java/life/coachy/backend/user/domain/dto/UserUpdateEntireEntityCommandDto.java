package life.coachy.backend.user.domain.dto;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class UserUpdateEntireEntityCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String username;
  @NotNull @NotEmpty private String displayName;
  @NotNull @NotEmpty private String password;
  @NotNull @NotEmpty private String email;
  @NotNull @NotEmpty private String avatar;
  @NotNull private Set<String> permissions;

  UserUpdateEntireEntityCommandDto(UserUpdateEntireEntityCommandDtoBuilder builder) {
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.password = builder.password;
    this.email = builder.email;
    this.avatar = builder.avatar;
    this.permissions = builder.permissions;
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

  public Set<String> getPermissions() {
    return this.permissions;
  }

}
