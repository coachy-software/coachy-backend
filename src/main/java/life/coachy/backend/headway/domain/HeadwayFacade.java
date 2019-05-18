package life.coachy.backend.headway.domain;

import java.net.URI;
import java.util.Set;
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class HeadwayFacade {

  private final HeadwayService headwayService;
  private final HeadwayCreator headwayCreator;
  private final UserFacade userFacade;

  HeadwayFacade(HeadwayService headwayService, HeadwayCreator headwayCreator, UserFacade userFacade) {
    this.headwayService = headwayService;
    this.headwayCreator = headwayCreator;
    this.userFacade = userFacade;
  }

  public URI save(HeadwayCreateCommandDto dto) {
    Headway headway = this.headwayService.save(this.headwayCreator.from(dto));
    this.userFacade.givePermissions(dto.getOwnerId(), "headway." + headway.identifier + ".delete");
    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.HEADWAYS + "/{id}").buildAndExpand(headway.identifier).toUri();
  }

  public void delete(ObjectId id) {
    this.headwayService.deleteById(id);
  }

  public Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId ownerId) {
    this.userFacade.ifExists(ownerId);
    return this.headwayService.fetchAllByOwnerId(ownerId);
  }

}
