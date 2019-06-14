package life.coachy.backend.request.domain;

import net.bytebuddy.utility.RandomString;

class RequestTokenGenerator {

  public static final int DEFAULT_LENGTH = 32;

  public String makeToken() {
    return this.makeToken(DEFAULT_LENGTH);
  }

  public String makeToken(int length) {
    return RandomString.make(length);
  }

}
