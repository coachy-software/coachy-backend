package life.coachy.backend.notification.domain;

import life.coachy.backend.notification.query.NotificationQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class NotificationConfiguration {

  @Bean
NotificationFacade notificationFacade(NotificationQueryRepository queryRepository, NotificationRepository repository) {
    NotificationCreator notificationCreator = new NotificationCreator();
    return new NotificationFacade(queryRepository, repository, notificationCreator);
  }

}
