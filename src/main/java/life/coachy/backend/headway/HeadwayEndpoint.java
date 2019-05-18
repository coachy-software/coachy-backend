package life.coachy.backend.headway;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Set;
import life.coachy.backend.headway.domain.HeadwayFacade;
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
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
    return ResponseEntity.created(this.headwayFacade.save(dto)).build();
  }

  @RequiresAuthenticated
  @RequiresPermissions("user.{id}.read")
  @ApiOperation("Displays all headways, that belong to specified owner id")
  @GetMapping("{id}")
  ResponseEntity<Set<HeadwayQueryDto>> fetchAll(@ApiParam("Headway's owner identifier") @PathVariable ObjectId id) {
    return ResponseEntity.ok(this.headwayFacade.fetchAllByOwnerId(id));
  }

  @ApiOperation("Deletes a headway")
  @RequiresPermissions("headway.{id}.delete")
  @RequiresAuthenticated
  @DeleteMapping("{id}")
  ResponseEntity<?> delete(@ApiParam("Headway identifier") ObjectId id) {
    this.headwayFacade.delete(id);
    return ResponseEntity.ok().build();
  }


}
