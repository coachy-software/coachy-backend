package life.coachy.backend.profile;

import life.coachy.backend.profile.domain.ProfileFacade;
import life.coachy.backend.user.domain.event.UserPostRegistrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ProfileEventListener {

  private final ProfileFacade profileFacade;

  ProfileEventListener(ProfileFacade profileFacade) {
    this.profileFacade = profileFacade;
  }

  @EventListener
  public void handleUserPostRegistrationEvent(UserPostRegistrationEvent event) {
    this.profileFacade.createProfile(event.getUserId());
  }

}
