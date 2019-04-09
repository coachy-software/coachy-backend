package life.coachy.backend.user.domain.dto;

import org.bson.types.ObjectId;

public final class UserUpdateCommandDtoBuilder {

  String username;
  String displayName;
  String email;
  String avatar;

  private UserUpdateCommandDtoBuilder() {}

  public static UserUpdateCommandDtoBuilder create() {
    return new UserUpdateCommandDtoBuilder();
  }

  public UserUpdateCommandDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserUpdateCommandDtoBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public UserUpdateCommandDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserUpdateCommandDtoBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserUpdateCommandDto build() {
    return new UserUpdateCommandDto(this);
  }

}
