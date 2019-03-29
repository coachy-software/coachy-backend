package life.coachy.backend.user.domain

import life.coachy.backend.user.domain.dto.AccountTypeDto
import spock.lang.Specification

class AccountTypeSpec extends Specification {

  def "method 'dto' should map enum's field to enum dto's field"() {
    when:
      AccountTypeDto charge = AccountType.CHARGE.dto()
      AccountTypeDto coach = AccountType.COACH.dto()
    then:
      charge == AccountTypeDto.CHARGE
      coach == AccountTypeDto.COACH
  }

}
