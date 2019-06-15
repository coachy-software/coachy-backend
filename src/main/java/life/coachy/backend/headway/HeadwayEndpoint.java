package life.coachy.backend.headway;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import java.util.Set;
import life.coachy.backend.headway.domain.HeadwayFacade;
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.infrastructure.authentication.AuthenticatedUser;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.HEADWAYS)
class HeadwayEndpoint {

  private final HeadwayFacade headwayFacade;

  @Autowired
  HeadwayEndpoint(HeadwayFacade headwayFacade) {
    this.headwayFacade = headwayFacade;
  }

  @ApiOperation("Creates a headway")
  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> create(@RequestBody HeadwayCreateCommandDto dto) {
    return ResponseEntity.created(this.headwayFacade.create(dto)).build();
  }

  @RequiresAuthenticated
  @RequiresPermissions("user.{id}.read")
  @ApiOperation("Displays all headways, that belong to specified owner id")
  @GetMapping("/by-owner/{id}")
  ResponseEntity<Set<HeadwayQueryDto>> fetchAll(@ApiParam("Headway's owner identifier") @PathVariable ObjectId id) {
    return ResponseEntity.ok(this.headwayFacade.fetchAllByOwnerId(id));
  }

  @ApiOperation("Deletes a headway")
  @RequiresPermissions("headway.{id}.delete")
  @RequiresAuthenticated
  @DeleteMapping("{id}")
  ResponseEntity<?> delete(@ApiParam("Headway identifier") @PathVariable ObjectId id) {
    this.headwayFacade.delete(id);
    return ResponseEntity.ok().build();
  }

  @RequiresPermissions("headway.{id}.read")
  @ApiOperation("Displays one headway by its id")
  @RequiresAuthenticated
  @GetMapping("{id}")
  ResponseEntity<HeadwayQueryDto> fetchOne(@ApiParam("Headway's id") @PathVariable ObjectId id) {
    return ResponseEntity.ok(this.headwayFacade.fetchOne(id));
  }

  @RequiresPermissions("headway.{id}.read")
  @ApiOperation("Shares the headway with some other user")
  @RequiresAuthenticated
  @PostMapping("{id}/share")
  ResponseEntity<?> share(@ApiParam("Headway's id") @PathVariable ObjectId id, @RequestBody Map<String, String> payload,
      @AuthenticatedUser UserQueryDto sender) {
    if (payload.get("shareTo") == null) {
      return ResponseEntity.badRequest().build();
    }

    this.headwayFacade.share(id, payload.get("shareTo"), sender);
    return ResponseEntity.ok().build();
  }


}
