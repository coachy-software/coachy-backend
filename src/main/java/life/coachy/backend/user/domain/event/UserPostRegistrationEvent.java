package life.coachy.backend.user.domain.event;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEvent;

public class UserPostRegistrationEvent extends ApplicationEvent {

  private final ObjectId userId;

  public UserPostRegistrationEvent(Object source, ObjectId userId) {
    super(source);
    this.userId = userId;
  }

  public ObjectId getUserId() {
    return this.userId;
  }

}
