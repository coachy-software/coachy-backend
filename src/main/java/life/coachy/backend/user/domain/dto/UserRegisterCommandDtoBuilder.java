package life.coachy.backend.user.domain.dto;

import life.coachy.backend.infrastructure.util.Buildable;

public final class UserRegisterCommandDtoBuilder implements Buildable<UserRegisterCommandDto> {

  String email;
  String username;
  String password;
  String matchingPassword;
  AccountTypeDto accountType;

  private UserRegisterCommandDtoBuilder() {}

  public static UserRegisterCommandDtoBuilder create() {
    return new UserRegisterCommandDtoBuilder();
  }

  public UserRegisterCommandDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserRegisterCommandDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserRegisterCommandDtoBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserRegisterCommandDtoBuilder withMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
    return this;
  }

  public UserRegisterCommandDtoBuilder withAccountType(AccountTypeDto accountType) {
    this.accountType = accountType;
    return this;
  }

  @Override
  public UserRegisterCommandDto build() {
    return new UserRegisterCommandDto(this);
  }

}
