package life.coachy.backend.password.domain;

import java.util.Date;
import life.coachy.backend.infrastructure.util.Buildable;

final class PasswordResetBuilder implements Buildable<PasswordReset> {

  String email;
  String token;
  long version;
  Date createdAt;

  private PasswordResetBuilder() {}

  public static PasswordResetBuilder create() {
    return new PasswordResetBuilder();
  }

  public PasswordResetBuilder withEmail(String email) {
    this.email = email;
    return this;
  }

  public PasswordResetBuilder withToken(String token) {
    this.token = token;
    return this;
  }

  public PasswordResetBuilder withVersion(long version) {
    this.version = version;
    return this;
  }

  public PasswordResetBuilder withCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  @Override
  public PasswordReset build() {
    return new PasswordReset(this);
  }

}
