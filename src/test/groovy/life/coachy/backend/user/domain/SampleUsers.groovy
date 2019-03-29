package life.coachy.backend.user.domain

import groovy.transform.CompileStatic
import life.coachy.backend.user.domain.dto.AccountTypeDto
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto
import life.coachy.backend.user.domain.dto.UserRegisterCommandDtoBuilder

@CompileStatic
trait SampleUsers {

  UserRegisterCommandDto sampleRegistrationUser = UserRegisterCommandDtoBuilder.create()
      .withUsername("yang160")
      .withPassword("yang160")
      .withAccountType(AccountTypeDto.CHARGE)
      .withEmail("yang160@gmail.com")
      .withMatchingPassword("yang160")
      .build();

}
