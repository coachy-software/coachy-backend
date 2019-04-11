package life.coachy.backend.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.authentication.AuthenticatedUser;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.domain.dto.UserChangePasswordCommandDto;
import life.coachy.backend.user.domain.dto.UserRegisterCommandDto;
import life.coachy.backend.user.query.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiOperation("Unconventional POST operations")
@RestController
@RequestMapping(ApiLayers.USERS)
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

  @RequiresAuthenticated
  @ApiOperation("Displays current user details")
  @GetMapping("me")
  public ResponseEntity<UserQueryDto> me(@AuthenticatedUser UserQueryDto userQueryDto) {
    return ResponseEntity.ok(userQueryDto);
  }

  @RequiresAuthenticated
  @ApiOperation("Changes password")
  @ApiResponses({
      @ApiResponse(code = 204, message = "Successfully updated"),
      @ApiResponse(code = 400, message = "Old password does not match, validation error or request payload incorrect")
  })
  @PostMapping("change-password")
  public ResponseEntity<UserQueryDto> changePassword(@AuthenticatedUser UserQueryDto userQueryDto, @Valid @RequestBody UserChangePasswordCommandDto dto) {
    this.facade.changePassword(userQueryDto, dto);
    return ResponseEntity.noContent().build();
  }

}
