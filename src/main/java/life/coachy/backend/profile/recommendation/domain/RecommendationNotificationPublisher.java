package life.coachy.backend.profile.recommendation.domain;

import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.HashMap;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import life.coachy.backend.profile.recommendation.query.RecommendationQueryDto;
import life.coachy.backend.user.query.UserQueryDto;

class RecommendationNotificationPublisher {

  private final NotificationFacade notificationFacade;
  private final ObjectToJsonConverter toJsonConverter;

  RecommendationNotificationPublisher(NotificationFacade notificationFacade, ObjectToJsonConverter toJsonConverter) {
    this.notificationFacade = notificationFacade;
    this.toJsonConverter = toJsonConverter;
  }

  void publishGotRecommendationNotification(UserQueryDto sender, UserQueryDto recipient, RecommendationQueryDto recommendation) {
    NotificationMessageDto notification = this.makeNotification(sender, recipient)
        .withContent(this.toJsonConverter.convert(Maps.newHashMap(new HashMap<String, String>() {{
              this.put("link", "/profiles/" + recommendation.getProfileUserId());
              this.put("text", "got_recommendation");
            }})
        )).build();

    this.notificationFacade.sendNotificationToUser(notification);
  }

  void publishChangeRequestNotification(UserQueryDto sender, UserQueryDto recipient, RecommendationQueryDto recommendation) {
    NotificationMessageDto notification = this.makeNotification(sender, recipient)
        .withContent(this.toJsonConverter.convert(Maps.newHashMap(new HashMap<String, String>() {{
              this.put("link", "/profiles/" + recommendation.getProfileUserId() + "/change-recommendation/" + recommendation.getId());
              this.put("text", "requests_change");
            }})
        )).build();

    this.notificationFacade.sendNotificationToUser(notification);
  }

  private NotificationMessageDtoBuilder makeNotification(UserQueryDto sender, UserQueryDto recipient) {
    return NotificationMessageDtoBuilder.create()
        .withSenderName(sender.getUsername())
        .withSenderAvatar(sender.getAvatar())
        .withSenderId(sender.getIdentifier())
        .withType("ALERT")
        .withRecipientId(recipient.getIdentifier())
        .withCreatedAt(LocalDateTime.now());
  }

}
