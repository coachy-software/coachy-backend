package life.coachy.backend.authentication;

import javax.validation.Valid;
import life.coachy.backend.user.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/authenticate")
@RestController
class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @PostMapping
  public ResponseEntity<UserLoginDto> authenticate(@RequestBody @Valid UserLoginDto dto) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        dto.getUsername(),
        dto.getPassword()
    );
    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(dto);
  }

}
