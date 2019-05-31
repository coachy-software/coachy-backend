package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.query.NotificationQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

  @Bean
  NotificationFacade notificationFacade(NotificationQueryRepository queryRepository, NotificationSendingService sendingService, UserFacade userFacade) {
    NotificationCreator notificationCreator = new NotificationCreator();
    return new NotificationFacade(queryRepository, sendingService, notificationCreator, userFacade);
  }

}
