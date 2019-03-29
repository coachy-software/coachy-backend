package life.coachy.backend.user.domain

import com.google.common.collect.Sets
import groovy.transform.CompileStatic
import life.coachy.backend.user.domain.dto.*

@CompileStatic
trait SampleUsers {

  UserRegisterCommandDto sampleRegistrationUser = UserRegisterCommandDtoBuilder.create()
      .withUsername("yang160")
      .withPassword("yang160")
      .withAccountType(AccountTypeDto.CHARGE)
      .withEmail("yang160@gmail.com")
      .withMatchingPassword("yang160")
      .build()

  UserUpdateEntireEntityCommandDto sampleUpdateUser = UserUpdateEntireEntityCommandDtoBuilder.create()
      .withUsername("yang160_UPDATED")
      .withPassword("yang160")
      .withEmail("yang160@gmail.com")
      .withAvatar("http://www.google.com/avatars/yang160.png")
      .withDisplayName("yang160")
      .withPermissions(Sets.newHashSet())
      .build()

}
