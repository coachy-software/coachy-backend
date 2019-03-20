package life.coachy.backend.user.domain;

import com.google.common.collect.Sets;
import java.util.Collections;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserCreator {

  private final PasswordEncoder passwordEncoder;

  UserCreator(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  User from(UserRegisterCommandDto dto) {
    return User.builder()
        .withUsername(dto.getUsername())
        .withEmail(dto.getEmail())
        .withPassword(this.passwordEncoder.encode(dto.getPassword()))
        .withAccountType(AccountType.valueOf(dto.getAccountType().name()))
        .withPermissions(Collections.emptySet())
        .withRoles(Sets.newHashSet("USER"))
        .build();
  }

}
