package life.coachy.backend.profile.recommendation.domain;

import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.profile.domain.ProfileFacade;
import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RecommendationConfiguration {

  @Bean
  RecommendationFacade recommendationFacade(RecommendationService service, NotificationFacade notificationFacade, ObjectToJsonConverter toJsonConverter,
      UserFacade userFacade, ProfileFacade profileFacade) {
    RecommendationCreator recommendationCreator = new RecommendationCreator();
    RecommendationNotificationPublisher notificationPublisher = new RecommendationNotificationPublisher(notificationFacade, toJsonConverter);

    return new RecommendationFacade(service, recommendationCreator, notificationPublisher, profileFacade, userFacade);
  }

}
