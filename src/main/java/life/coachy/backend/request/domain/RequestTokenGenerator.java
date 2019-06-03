package life.coachy.backend.request.domain;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class RequestTokenGenerator {

  static final int DEFAULT_LENGTH = 32;
  private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

  public String makeToken() {
    return this.makeToken(DEFAULT_LENGTH);
  }

  public String makeToken(int length) {
    int leftLimit = 97; // 'a' char
    int rightLimit = 122; // 'z' char

    return IntStream.range(0, length)
        .map(i -> leftLimit + (int) (RANDOM.nextFloat() * (rightLimit - leftLimit + 1)))
        .mapToObj(randomLimitedInt -> String.valueOf((char) randomLimitedInt))
        .collect(Collectors.joining());
  }

}
