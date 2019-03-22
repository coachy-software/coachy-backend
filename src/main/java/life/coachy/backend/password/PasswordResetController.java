package life.coachy.backend.password;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.password.domain.PasswordResetFacade;
import life.coachy.backend.password.domain.dto.PasswordResetCommandDto;
import life.coachy.backend.password.query.PasswordResetQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PasswordResetController {

  private final PasswordResetFacade facade;

  @Autowired
  public PasswordResetController(PasswordResetFacade facade) {
    this.facade = facade;
  }

  @ApiOperation("Creates reset token")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Email not found"),
      @ApiResponse(code = 409, message = "Token already generated for that email"),
      @ApiResponse(code = 204, message = "Successfully created")
  })
  @PostMapping("/api/create-token/{email:.+}")
  public ResponseEntity<PasswordResetQueryDto> createToken(@PathVariable String email) {
    this.facade.generateToken(email);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Resets password")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Token is invalid or has expired"),
      @ApiResponse(code = 204, message = "Password reset")
  })
  @PostMapping("/api/reset-password/{token}")
  public ResponseEntity<PasswordResetCommandDto> resetPassword(
      @PathVariable @ApiParam("Reset-password token") String token,
      @RequestBody @Valid @ApiParam("Password reset data transfer object") PasswordResetCommandDto dto) {
    this.facade.resetPassword(token, dto);
    return ResponseEntity.noContent().build();
  }

}
