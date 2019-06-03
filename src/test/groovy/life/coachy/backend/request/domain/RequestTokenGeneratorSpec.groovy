package life.coachy.backend.request.domain

import spock.lang.Shared
import spock.lang.Specification

class RequestTokenGeneratorSpec extends Specification {

  @Shared RequestTokenGenerator tokenGenerator = new RequestTokenGenerator()

  def "'makeToken' method should return token, that length is equal to #DEFAULT_LENGTH"() {
    given:
      String token = this.tokenGenerator.makeToken()
    expect:
      token.length() == 32
  }

  def "'makeToken(length)' method should return token with specfied length"() {
    given:
      String token = this.tokenGenerator.makeToken(21)
    expect:
      token.length() == 21
  }

}
