package life.coachy.backend.user.domain;

import java.util.Collection;
import life.coachy.backend.user.query.UserQueryDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

class UserDetails extends User {

  private final UserQueryDto userQueryDto;

  UserDetails(UserQueryDto userQueryDto, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.userQueryDto = userQueryDto;
  }

  @Override
  public String toString() {
    return this.userQueryDto.toString();
  }

}
