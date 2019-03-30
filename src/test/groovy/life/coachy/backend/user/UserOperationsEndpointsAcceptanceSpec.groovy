package life.coachy.backend.user

import life.coachy.backend.base.IntegrationSpec

class UserOperationsEndpointsAcceptanceSpec extends IntegrationSpec {

  def "positive register scenario"() {
    given: "we have no users"
    when: "I go to /api/users/register"
    then: "I have got registered"
    when: "I go to /api/users/me"
    then: "I see details about my account"
  }

}
