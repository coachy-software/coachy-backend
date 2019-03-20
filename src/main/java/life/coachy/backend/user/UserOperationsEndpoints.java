package life.coachy.backend.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Unconventional POST operations")
@RestController
@RequestMapping("api/users")
class UserOperationsEndpoints {

  private final UserFacade facade;

  @Autowired
  public UserOperationsEndpoints(UserFacade facade) {
    this.facade = facade;
  }

  @ApiOperation("Creates an user")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Successfully created"),
      @ApiResponse(code = 400, message = "Validation error"),
      @ApiResponse(code = 409, message = "User with that email/username already exists")
  })
  @PostMapping("register")
  public ResponseEntity<UserRegisterCommandDto> create(@Valid @RequestBody UserRegisterCommandDto dto) {
    this.facade.register(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
