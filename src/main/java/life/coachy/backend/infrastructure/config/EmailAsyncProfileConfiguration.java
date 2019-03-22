package life.coachy.backend.infrastructure.config;

import life.coachy.backend.infrastructure.constants.Profiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@Profile("!" + Profiles.TEST)
class EmailAsyncProfileConfiguration {

}
