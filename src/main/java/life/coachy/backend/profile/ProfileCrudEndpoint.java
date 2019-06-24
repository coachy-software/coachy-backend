package life.coachy.backend.profile;

import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermission;
import life.coachy.backend.profile.domain.ProfileFacade;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;
import life.coachy.backend.profile.query.ProfileQueryBinder;
import life.coachy.backend.profile.query.ProfileQueryDto;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.PROFILES)
class ProfileCrudEndpoint {

  private final ProfileFacade profileFacade;

  @Autowired
  ProfileCrudEndpoint(ProfileFacade profileFacade) {
    this.profileFacade = profileFacade;
  }

  @ApiOperation("Displays profiles that matches criteria, only if criteria present, otherwise displays all profiles")
  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<List<ProfileQueryDto>> fetchAll(@QuerydslPredicate(bindings = ProfileQueryBinder.class) Predicate predicate, Pageable pageable) {
    return ResponseEntity.ok(this.profileFacade.fetchAll(predicate, pageable));
  }

  @ApiOperation("Displays profile's details")
  @RequiresAuthenticated
  @RequiresPermission("user.{id}.read")
  @GetMapping("{id}")
  ResponseEntity<ProfileQueryDto> fetchOne(@PathVariable ObjectId id) {
    return ResponseEntity.ok(this.profileFacade.fetchByUserId(id));
  }

  @ApiOperation("Displays profile's followers")
  @RequiresAuthenticated
  @RequiresPermission("user.{id}.read")
  @GetMapping("{id}/followers")
  ResponseEntity<Map<String, Set<UserQueryDto>>> fetchFollowers(@PathVariable ObjectId id) {
    return ResponseEntity.ok(Collections.singletonMap("followers", this.profileFacade.fetchFollowers(id)));
  }

  @ApiOperation("Displays following profiles by specified profile")
  @RequiresAuthenticated
  @RequiresPermission("user.{id}.read")
  @GetMapping("{id}/following")
  ResponseEntity<Map<String, Set<UserQueryDto>>> fetchFollowing(@PathVariable ObjectId id) {
    return ResponseEntity.ok(Collections.singletonMap("following", this.profileFacade.fetchFollowing(id)));
  }

  @ApiOperation("Updates the whole profile entity")
  @RequiresAuthenticated
  @RequiresPermission("user.{id}.update")
  @PutMapping("{id}")
  ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody @Valid ProfileUpdateCommandDto dto) {
    this.profileFacade.updateProfile(dto, id);
    return ResponseEntity.noContent().build();
  }

}
