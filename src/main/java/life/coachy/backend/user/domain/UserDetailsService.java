package life.coachy.backend.user.domain;

import com.google.common.base.Preconditions;
import java.util.Set;
import java.util.stream.Collectors;
import life.coachy.backend.user.query.UserQueryDto;
import life.coachy.backend.user.query.UserQueryDtoRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserQueryDtoRepository repository;

  public UserDetailsService(UserQueryDtoRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Preconditions.checkNotNull(username, "Username cannot be null");
    UserQueryDto readOnlyUser = this.repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    return new User(username, readOnlyUser.getPassword(), this.mapRolesToGrantedAuthority(readOnlyUser.getRoles()));
  }

  private Set<? extends GrantedAuthority> mapRolesToGrantedAuthority(Set<String> roles) {
    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
  }

}
