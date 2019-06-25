package life.coachy.backend.profile.recommendation.domain;

import java.util.Set;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;

class RecommendationFacade {

  private final RecommendationService recommendationService;
  private final RecommendationCreator recommendationCreator;
  private final NotificationFacade notificationFacade;
  private final UserFacade userFacade;

  RecommendationFacade(RecommendationService recommendationService, RecommendationCreator recommendationCreator, NotificationFacade notificationFacade,
      UserFacade userFacade) {
    this.recommendationService = recommendationService;
    this.recommendationCreator = recommendationCreator;
    this.notificationFacade = notificationFacade;
    this.userFacade = userFacade;
  }

  public void create(RecommendationCreateCommandDto dto) {
    this.recommendationService.save(this.recommendationCreator.from(dto));
  }

  public void requestForChange(ObjectId recommendationId) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);

    UserQueryDto sender = this.userFacade.fetchOne(queryDto.getProfileUserId());
    UserQueryDto recipient = this.userFacade.fetchOne(queryDto.getFrom());

    this.userFacade.givePermissions(recipient.getIdentifier(), "recommendation." + queryDto.getId() + ".update");
    this.notificationFacade.sendNotificationToUser(this.recommendationService.makeNotificationMessage(sender, recipient, queryDto));
  }

  public void commitChange(ObjectId recommendationId, RecommendationUpdateCommandDto dto) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    this.recommendationService.commitChange(this.recommendationCreator.from(queryDto), dto);

    this.userFacade.nullifyPermissions(queryDto.getFrom(), queryDto.getId());
  }

  public void changeVisibleStatus(ObjectId recommendationId, boolean status) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    this.recommendationService.updateStatus(this.recommendationCreator.from(queryDto), status);
  }

  public Set<RecommendationQueryDto> fetchAll(ObjectId profileUserId) {
    return this.recommendationService.fetchAllByProfileUserId(profileUserId);
  }

}
