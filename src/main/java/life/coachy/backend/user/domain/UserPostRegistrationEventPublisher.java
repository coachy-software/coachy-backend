package life.coachy.backend.user.domain;

import life.coachy.backend.user.domain.event.UserPostRegistrationEvent;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class UserPostRegistrationEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  UserPostRegistrationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  void publish(ObjectId userId) {
    UserPostRegistrationEvent userPostRegistrationEvent = new UserPostRegistrationEvent(this, userId);
    this.applicationEventPublisher.publishEvent(userPostRegistrationEvent);
  }


}
