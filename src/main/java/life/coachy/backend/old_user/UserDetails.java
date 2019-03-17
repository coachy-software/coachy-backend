package life.coachy.backend.old_user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

class UserDetails extends org.springframework.security.core.userdetails.User {

  private final User user;

  UserDetails(User user, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

}