package life.coachy.backend.headway.domain;

import java.net.URI;
import java.util.Set;
import life.coachy.backend.headway.domain.dto.HeadwayCreateCommandDto;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class HeadwayFacade {

  private final HeadwayService headwayService;
  private final HeadwayCreator headwayCreator;
  private final NotificationFacade notificationFacade;
  private final UserFacade userFacade;

  HeadwayFacade(HeadwayService headwayService, HeadwayCreator headwayCreator, NotificationFacade notificationFacade, UserFacade userFacade) {
    this.headwayService = headwayService;
    this.headwayCreator = headwayCreator;
    this.notificationFacade = notificationFacade;
    this.userFacade = userFacade;
  }

  public URI create(HeadwayCreateCommandDto dto) {
    Headway headway = this.headwayService.save(this.headwayCreator.from(dto));
    this.userFacade.givePermissions(dto.getOwnerId(), "headway." + headway.identifier + ".delete", "headway." + headway.identifier + ".read");

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.HEADWAYS + "/{id}").buildAndExpand(headway.identifier).toUri();
  }

  public void delete(ObjectId id) {
    this.headwayService.deleteById(id);
  }

  public HeadwayQueryDto fetchOne(ObjectId id) {
    return this.headwayService.fetchOne(id);
  }

  public Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId ownerId) {
    this.userFacade.ifExists(ownerId);
    return this.headwayService.fetchAllByOwnerId(ownerId);
  }

  public void share(ObjectId headwayId, String userId, UserQueryDto sender) {
    HeadwayQueryDto queryDto = this.fetchOne(headwayId);
    UserQueryDto userQueryDto = this.userFacade.fetchOne(new ObjectId(userId));

    String permission = "headway." + queryDto.getIdentifier() + ".read";
    this.userFacade.throwIfHasPermission(new ObjectId(userId), permission);

    this.userFacade.givePermissions(userQueryDto.getIdentifier(), permission);
    this.notificationFacade.sendNotificationToUser(this.headwayService.makeNotificationMessage(sender, userQueryDto, headwayId));
  }

}
