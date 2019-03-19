package life.coachy.backend.old_user.password;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import javax.validation.Valid;
import life.coachy.backend.email.EmailFacade;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PasswordResetController {

//  private final UserFacade userFacade;
  private final EmailFacade emailService;
  private final PasswordResetTokenRepository repository;

  @Value("${frontend.uri}")
  private String resetLink;

  @Autowired
  public PasswordResetController(EmailFacade emailFacade,
      PasswordResetTokenRepository repository) {
//    this.userFacade = userFacade;
    this.emailService = emailFacade;
    this.repository = repository;
  }

  @ApiOperation("Creates token that need be used to reset password")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Email cannot be found"),
      @ApiResponse(code = 409, message = "Token already craeted"),
      @ApiResponse(code = 204, message = "Token created")
  })
  @PostMapping("/api/create-token/{email:.+}")
  public ResponseEntity<PasswordResetTokenDto> createToken(@PathVariable @ApiParam("Requester's email") String email) {
//    if (!this.userFacade.exists(email)) {
//      return ResponseEntity.notFound().build();
//    }

    if (this.repository.findById(email).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    PasswordResetToken token = new PasswordResetToken(email, RandomString.make(32));
    this.repository.save(token);

    this.emailService.sendResetPasswordEmail(email, this.resetLink + "reset-password/" + token.getToken());
    return ResponseEntity.noContent().build();
  }

  @ApiOperation("Resets password")
  @ApiResponses({
      @ApiResponse(code = 404, message = "Token is invalid or has expired"),
      @ApiResponse(code = 204, message = "Password reset")
  })
  @PostMapping("/api/reset-password/{token}")
  public ResponseEntity<PasswordResetTokenDto> resetPassword(
      @PathVariable @ApiParam("Reset-password token") String token,
      @RequestBody @Valid @ApiParam("Password reset data transfer object") PasswordResetTokenDto dto) {
    Optional<PasswordResetToken> passwordResetToken = this.repository.findByToken(token);

    if (!passwordResetToken.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    String email = passwordResetToken.get().getEmail();
//    this.userFacade.resetPassword(email, dto.getPassword());
    this.repository.deleteById(email);

    return ResponseEntity.noContent().build();
  }

}
