package life.coachy.backend.user.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class UserUpdateCommandDto implements CommandDtoMarker {

  @NotNull @NotEmpty private String username;
  @NotNull @NotEmpty private String displayName;
  @NotNull @NotEmpty private String email;
  @NotNull @NotEmpty private String avatar;

  UserUpdateCommandDto() {}

  UserUpdateCommandDto(UserUpdateCommandDtoBuilder builder) {
    this.username = builder.username;
    this.displayName = builder.displayName;
    this.email = builder.email;
    this.avatar = builder.avatar;
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

}
