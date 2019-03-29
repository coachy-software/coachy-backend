package life.coachy.backend.user.domain

import life.coachy.backend.user.domain.dto.AccountTypeDto
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto
import life.coachy.backend.user.domain.dto.UserRegisterCommandDtoBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserCreatorSpec extends Specification {

  PasswordEncoder passwordEncoder = Mock(PasswordEncoder);

  def "'UserRegisterCommandDto' to 'User' test"() {
    given: "user data transfer object"
      UserRegisterCommandDto dto = UserRegisterCommandDtoBuilder.create()
          .withUsername("yang160")
          .withEmail("yang160@gmail.com")
          .withPassword("password123")
          .withAccountType(AccountTypeDto.CHARGE)
          .withMatchingPassword("password123")
          .build();
    when: "map user dto to user entity"
      User user = new UserCreator(passwordEncoder).from(dto);
    then:
      user != null
  }

}
