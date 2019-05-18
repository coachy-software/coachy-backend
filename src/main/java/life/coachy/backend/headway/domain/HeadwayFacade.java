package life.coachy.backend.headway.domain;

import java.util.Set;
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;

public class HeadwayFacade {

  private final HeadwayService headwayService;
  private final HeadwayCreator headwayCreator;
  private final UserFacade userFacade;

  HeadwayFacade(HeadwayService headwayService, HeadwayCreator headwayCreator, UserFacade userFacade) {
    this.headwayService = headwayService;
    this.headwayCreator = headwayCreator;
    this.userFacade = userFacade;
  }

  public void create(HeadwayCreateCommandDto dto) {
    Headway headway = this.headwayService.save(this.headwayCreator.from(dto));
    this.userFacade.givePermissions(dto.getOwnerId(), "headway." + headway.identifier + ".delete");
  }

  public void delete(ObjectId id) {
    this.headwayService.deleteById(id);
  }

  public Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId ownerId) {
    this.userFacade.ifExists(ownerId);
    return this.headwayService.fetchAllByOwnerId(ownerId);
  }

}
