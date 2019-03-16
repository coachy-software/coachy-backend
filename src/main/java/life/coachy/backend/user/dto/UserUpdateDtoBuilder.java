package life.coachy.backend.user.dto;

import life.coachy.backend.util.Buildable;
import org.bson.types.ObjectId;

public final class UserUpdateDtoBuilder implements Buildable<UserUpdateDto> {

  String username;
  String displayName;
  String password;
  String email;
  String avatar;
  ObjectId boardIdentifier;

  private UserUpdateDtoBuilder() {}

  public static UserUpdateDtoBuilder createBuilder() {
    return new UserUpdateDtoBuilder();
  }

  public UserUpdateDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserUpdateDtoBuilder withDisplayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public UserUpdateDtoBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserUpdateDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserUpdateDtoBuilder withAvatar(String avatar) {
    this.avatar = avatar;
    return this;
  }

  public UserUpdateDtoBuilder withBoardIdentifier(ObjectId boardIdentifier) {
    this.boardIdentifier = boardIdentifier;
    return this;
  }

  @Override
  public UserUpdateDto build() {
    return new UserUpdateDto(this);
  }

}
