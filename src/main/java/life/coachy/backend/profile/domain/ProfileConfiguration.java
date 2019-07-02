package life.coachy.backend.profile.domain;

import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.profile.query.ProfileQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProfileConfiguration {

  @Bean
  ProfileFacade profileFacade(ProfileService profileService, ProfileQueryRepository queryRepository, QueryOperationsFactory queryOperationsFactory,
      NotificationFacade notificationFacade, UserFacade userFacade) {
    ProfileCreator creator = new ProfileCreator();
    ProfileConverter profileConverter = new ProfileConverter();

    return new ProfileFacade(creator, profileService, queryRepository, queryOperationsFactory, notificationFacade, userFacade, profileConverter);
  }

}
