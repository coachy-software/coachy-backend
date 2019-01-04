package life.coachy.backend.authentication;

import javax.validation.Valid;
import life.coachy.backend.user.UserFacade;
import life.coachy.backend.user.UserRegistrationDto;
import life.coachy.backend.util.RequestUtil;
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

  @PostMapping
  public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationDto dto, BindingResult result) {
    if (result.hasErrors()) {
      return RequestUtil.errorResponse(result);
    }

    return this.userFacade.register(dto);
  }

}
