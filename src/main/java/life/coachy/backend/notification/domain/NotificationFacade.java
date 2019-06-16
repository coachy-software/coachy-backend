package life.coachy.backend.notification.domain;

import java.util.Map;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import life.coachy.backend.notification.query.NotificationQueryDto;
import life.coachy.backend.notification.query.NotificationQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class NotificationFacade {

  private final NotificationQueryRepository queryRepository;
  private final NotificationService service;
  private final NotificationCreator creator;
  private final UserFacade userFacade;

  NotificationFacade(NotificationQueryRepository queryRepository, NotificationService service, NotificationCreator creator,
      UserFacade userFacade) {
    this.queryRepository = queryRepository;
    this.service = service;
    this.creator = creator;
    this.userFacade = userFacade;
  }

  public Page<NotificationQueryDto> fetchAllByRecipientId(ObjectId id, Pageable pageable) {
    return this.queryRepository.findAllByRecipientIdOrderByCreatedAtDesc(id, pageable);
  }

  public void sendNotificationToUser(NotificationMessageDto dto) {
    UserQueryDto userQueryDto = this.userFacade.fetchOne(dto.getRecipientId());
    this.service.sendNotification(this.creator.from(dto), dto, userQueryDto);
  }

  public void sendNotificationToAllUsers(NotificationMessageDtoBuilder dtoBuilder) {
    this.userFacade.fetchAll().forEach(userQueryDto -> {
      dtoBuilder.withRecipientId(userQueryDto.getIdentifier());
      this.sendNotificationToUser(dtoBuilder.build());
    });
  }

  public void markAllAsRead(ObjectId recipientId) {
    this.userFacade.ifExists(recipientId);
    this.queryRepository.findAllByRecipientId(recipientId).forEach((queryDto) -> this.service.markAsRead(this.creator.from(queryDto), queryDto));
  }

  public boolean hasAnyUnread(ObjectId recipientId) {
    this.userFacade.ifExists(recipientId);
    return this.queryRepository.existsByRecipientIdAndReadIsFalse(recipientId);
  }

  public Map<String, String> convertAlertContentToJson(String message, String link) {
    return this.service.convertAlertContentToJson(message, link);
  }

}
