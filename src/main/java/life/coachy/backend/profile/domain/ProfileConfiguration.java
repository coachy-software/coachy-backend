package life.coachy.backend.profile.domain;

import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.profile.query.ProfileQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProfileConfiguration {

  @Bean
  ProfileFacade profileFacade(ProfileService profileService, ProfileQueryRepository queryRepository, QueryOperationsFactory queryOperationsFactory) {
    ProfileCreator creator = new ProfileCreator();
    return new ProfileFacade(creator, profileService, queryRepository, queryOperationsFactory);
  }

}
