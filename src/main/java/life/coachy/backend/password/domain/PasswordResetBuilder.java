package life.coachy.backend.password.domain;

import life.coachy.backend.infrastructure.util.Buildable;

final class PasswordResetBuilder implements Buildable<PasswordReset> {

  String email;
  String token;

  private PasswordResetBuilder() {}

  public static PasswordResetBuilder create() {
    return new PasswordResetBuilder();
  }

  PasswordResetBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  PasswordResetBuilder withToken(String token) {
    this.token = token;
    return this;
  }

  @Override
  public PasswordReset build() {
    return new PasswordReset(this);
  }

}
