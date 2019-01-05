package life.coachy.backend.authentication;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.user.UserAuthenticationDto;
import life.coachy.backend.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
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

  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error occured"),
      @ApiResponse(code = 401, message = "Bad credentials"),
      @ApiResponse(code = 200, message = "User logged in")
  })
  @PostMapping
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid @ApiParam("User data transfer object") UserAuthenticationDto dto,
      BindingResult result) {
    if (result.hasErrors()) {
      return RequestUtil.errorResponse(result);
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        dto.getUsername(),
        dto.getPassword()
    );

    Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

    if (authentication == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.ok(dto.toEntity());
  }

}
