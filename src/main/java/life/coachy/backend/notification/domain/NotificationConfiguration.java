package life.coachy.backend.notification.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

  @Bean
  NotificationFacade notificationFacade(NotificationService service) {
    NotificationCreator notificationCreator = new NotificationCreator();
    return new NotificationFacade(service, notificationCreator);
  }

}
