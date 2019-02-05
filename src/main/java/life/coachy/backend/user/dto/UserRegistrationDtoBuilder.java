package life.coachy.backend.user.dto;

import life.coachy.backend.util.Buildable;

public final class UserRegistrationDtoBuilder implements Buildable<UserRegistrationDto> {

  String username;
  String password;
  String matchingPassword;
  String email;
  String accountType;

  private UserRegistrationDtoBuilder() {}

  public static UserRegistrationDtoBuilder createBuilder() {
    return new UserRegistrationDtoBuilder();
  }

  public UserRegistrationDtoBuilder withUsername(String username) {
    this.username = username;
    return this;
  }

  public UserRegistrationDtoBuilder withPassword(String password) {
    this.password = password;
    return this;
  }

  public UserRegistrationDtoBuilder withMatchingPassword(String matchingPassword) {
    this.matchingPassword = matchingPassword;
    return this;
  }

  public UserRegistrationDtoBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public UserRegistrationDtoBuilder withAccountType(String accountType) {
    this.accountType = accountType;
    return this;
  }

  @Override
  public UserRegistrationDto build() {
    return new UserRegistrationDto(this);
  }

}
