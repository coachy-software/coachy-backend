package life.coachy.backend.user;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@DependsOn("passwordEncoder")
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  UserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optionalUser = this.userRepository.findByUsername(username);

    if (!optionalUser.isPresent()) {
      throw new UsernameNotFoundException("Username not found!");
    }

    User user = optionalUser.get();
    String encodedPassword = this.passwordEncoder.encode(user.getPassword());

    return new UserDetails(user, username, encodedPassword, this.mapRolesToGrantedAuthority(user.getRoles()));
  }

  private Set<? extends GrantedAuthority> mapRolesToGrantedAuthority(Set<String> roles) {
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

}

