package life.coachy.backend.profile.recommendation.domain;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.profile.domain.ProfileFacade;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationCreateCommandDto;
import life.coachy.backend.profile.recommendation.domain.dto.RecommendationUpdateCommandDto;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RecommendationFacade {

  private final RecommendationService recommendationService;
  private final RecommendationCreator recommendationCreator;
  private final RecommendationNotificationPublisher notificationPublisher;
  private final ProfileFacade profileFacade;
  private final UserFacade userFacade;

  RecommendationFacade(RecommendationService recommendationService, RecommendationCreator recommendationCreator,
      RecommendationNotificationPublisher notificationPublisher, ProfileFacade profileFacade, UserFacade userFacade) {
    this.recommendationService = recommendationService;
    this.recommendationCreator = recommendationCreator;
    this.notificationPublisher = notificationPublisher;
    this.profileFacade = profileFacade;
    this.userFacade = userFacade;
  }

  public URI create(RecommendationCreateCommandDto dto) {
    this.profileFacade.fetchByUserId(dto.getProfileUserId());

    Recommendation recommendation = this.recommendationService.save(this.recommendationCreator.from(dto));
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendation.id);

    UserQueryDto sender = this.userFacade.fetchOne(queryDto.getFrom());
    UserQueryDto recipient = this.userFacade.fetchOne(queryDto.getProfileUserId());

    this.userFacade.givePermissions(recipient.getIdentifier(), "recommendation." + recommendation.id + ".owner");
    this.notificationPublisher.publishGotRecommendationNotification(sender, recipient, queryDto);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path(ApiLayers.PROFILES + "/{id}/" + ApiLayers.RECOMMENDATIONS + "/{recommendationId}")
        .buildAndExpand(recipient.getIdentifier(), recommendation.id).toUri();
  }

  public void requestRevision(ObjectId recommendationId) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    this.recommendationService.throwIfRequestAlreadySent(queryDto.getFrom(), queryDto.getId());

    UserQueryDto sender = this.userFacade.fetchOne(queryDto.getProfileUserId());
    UserQueryDto recipient = this.userFacade.fetchOne(queryDto.getFrom());

    this.userFacade.givePermissions(recipient.getIdentifier(), "recommendation." + queryDto.getId() + ".update");
    this.notificationPublisher.publishChangeRequestNotification(sender, recipient, queryDto);
  }

  public void commitRevision(ObjectId recommendationId, RecommendationUpdateCommandDto dto) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    this.recommendationService.commitChange(this.recommendationCreator.from(queryDto), dto);

    this.userFacade.nullifyPermissions(queryDto.getFrom(), queryDto.getId());
  }

  public void changeVisibilityStatus(ObjectId recommendationId, boolean status) {
    RecommendationQueryDto queryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    this.recommendationService.updateStatus(this.recommendationCreator.from(queryDto), status);
  }

  public Map<String, Object> fetchOne(ObjectId recommendationId) {
    RecommendationQueryDto recommendationQueryDto = this.recommendationService.fetchOneOrThrow(recommendationId);
    return this.recommendationService.convertAndAppendCreatorDetails(recommendationQueryDto, this.userFacade.fetchOne(recommendationQueryDto.getFrom()));
  }

  public Set<Map<String, Object>> fetchAll(ObjectId profileUserId) {
    Set<RecommendationQueryDto> recommendationQueryDtos = this.recommendationService.fetchAllByProfileUserId(profileUserId);
    return this.recommendationService.convertAndAppendCreatorDetailsForEach(recommendationQueryDtos);
  }

}
