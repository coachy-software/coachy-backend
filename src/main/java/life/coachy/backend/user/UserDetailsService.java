package life.coachy.backend.user;

import java.util.Optional;
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
  public UserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUser = this.userRepository.findByUsername(username);

    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("Username not found!");
    }

    User user = optionalUser.get();
    return new UserDetails(user, username, user.getPassword(), this.mapRolesToGrantedAuthority(user.getRoles())); // Password provider
  }

  private Set<? extends GrantedAuthority> mapRolesToGrantedAuthority(Set<String> roles) {
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

}
