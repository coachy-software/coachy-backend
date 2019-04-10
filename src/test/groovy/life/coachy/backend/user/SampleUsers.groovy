package life.coachy.backend.user

import groovy.transform.CompileStatic
import life.coachy.backend.user.domain.dto.*
import org.bson.types.ObjectId

@CompileStatic
trait SampleUsers {

  UserRegisterCommandDto sampleRegistrationUser = UserRegisterCommandDtoBuilder.create()
      .withUsername("yang160")
      .withPassword("yang160")
      .withMatchingPassword("yang160")
      .withAccountType(AccountTypeDto.CHARGE)
      .withEmail("yang160@gmail.com")
      .build()

  UserUpdateCommandDto sampleUpdateUser = UserUpdateCommandDtoBuilder.create()
      .withUsername("yang160_UPDATED")
      .withEmail("yang160@gmail.com")
      .withAvatar("http://www.google.com/avatars/yang160.png")
      .withDisplayName("yang160")
      .build()

  UserUpdateCommandDto wrongSampleUpdateUser = UserUpdateCommandDtoBuilder.create()
      .withAvatar("http://www.google.com/avatars/yang160.png")
      .withDisplayName("yang160")
      .build()

}
