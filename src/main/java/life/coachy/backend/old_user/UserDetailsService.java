package life.coachy.backend.old_user;

import com.google.common.base.Preconditions;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  UserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Preconditions.checkNotNull(username, "Username cannot be null");

    User user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

    return new UserDetails(user, username, user.getPassword(), this.mapRolesToGrantedAuthority(user.getRoles()));
  }

  private Set<? extends GrantedAuthority> mapRolesToGrantedAuthority(Set<String> roles) {
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

}

