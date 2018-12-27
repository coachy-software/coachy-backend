package life.coachy.backend.authentication;

import com.google.common.base.Strings;
import java.util.Map;
import life.coachy.backend.user.User;
import life.coachy.backend.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/authenticate")
class AuthenticateController {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticateController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping
  public ResponseEntity<User> authenticate(@RequestBody Map<String, String> parameters) {
    String username = parameters.get("username");
    String password = parameters.get("password");

    if (parameters.size() != 2 || Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
      return ResponseEntity.badRequest().build();
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    User user = ((UserDetails) authentication.getPrincipal()).getUser();
    return ResponseEntity.ok(user);
  }

}
