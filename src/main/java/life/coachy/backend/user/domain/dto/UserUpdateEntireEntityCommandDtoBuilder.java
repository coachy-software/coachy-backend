package life.coachy.backend.user.domain.dto;

import java.util.Set;

public final class UserUpdateEntireEntityCommandDtoBuilder {

  String username;
  String displayName;
  String password;
  String email;
  String avatar;
  Set<String> permissions;

  private UserUpdateEntireEntityCommandDtoBuilder() {}

  public static UserUpdateEntireEntityCommandDtoBuilder create() {
    return new UserUpdateEntireEntityCommandDtoBuilder();
  }

  public UserUpdateEntireEntityCommandDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserUpdateEntireEntityCommandDtoBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public UserUpdateEntireEntityCommandDtoBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserUpdateEntireEntityCommandDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserUpdateEntireEntityCommandDtoBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserUpdateEntireEntityCommandDtoBuilder withPermissions(Set<String> permissions) {
    this.permissions = permissions;
    return this;
  }

  public UserUpdateEntireEntityCommandDto build() {
    return new UserUpdateEntireEntityCommandDto(this);
  }

}
