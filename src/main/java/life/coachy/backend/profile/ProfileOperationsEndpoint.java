package life.coachy.backend.profile;

import io.swagger.annotations.ApiOperation;
import life.coachy.backend.infrastructure.authentication.AuthenticatedUser;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermission;
import life.coachy.backend.profile.domain.ProfileFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.PROFILES)
class ProfileOperationsEndpoint {

  private final ProfileFacade profileFacade;

  @Autowired
  ProfileOperationsEndpoint(ProfileFacade profileFacade) {
    this.profileFacade = profileFacade;
  }

  @ApiOperation("Follows specified profile")
  @RequiresAuthenticated
  @RequiresPermission("user.{userId}.update")
  @PostMapping("{id}/follow")
  ResponseEntity<?> follow(@PathVariable("id") ObjectId userId, @AuthenticatedUser UserQueryDto queryDto) {
    this.profileFacade.follow(userId, queryDto.getIdentifier());
    return ResponseEntity.ok().build();
  }

  @ApiOperation("Undoes the follow from specified profile")
  @RequiresAuthenticated
  @RequiresPermission("user.{userId}.update")
  @PostMapping("{id}/unfollow")
  ResponseEntity<?> unfollow(@PathVariable("id") ObjectId userId, @AuthenticatedUser UserQueryDto queryDto) {
    this.profileFacade.unfollow(userId, queryDto.getIdentifier());
    return ResponseEntity.ok().build();
  }


}
