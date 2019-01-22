package life.coachy.backend.authentication;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.user.UserFacade;
import life.coachy.backend.user.UserRegistrationDto;
import life.coachy.backend.util.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/register")
@RestController
class RegistrationController {

  private final UserFacade userFacade;

  @Autowired
  public RegistrationController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @ApiResponses({
      @ApiResponse(code = 400, message = "Validation error occured"),
      @ApiResponse(code = 409, message = "User already exists"),
      @ApiResponse(code = 201, message = "User created")
  })
  @PostMapping
  public ResponseEntity<?> register(
      @RequestBody @Valid @ApiParam("User data transfer object") UserRegistrationDto dto,
      BindingResult result) {
    if (result.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationUtil.toDto(result.getFieldErrors()));
    }

    return this.userFacade.register(dto);
  }

}
