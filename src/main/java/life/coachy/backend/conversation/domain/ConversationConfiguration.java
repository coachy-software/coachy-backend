package life.coachy.backend.conversation.domain;

import life.coachy.backend.user.domain.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConversationConfiguration {

  @Bean
  public ConversationFacade conversationFacade(ConversationService service, UserFacade userFacade) {
    ConversationCreator creator = new ConversationCreator();
    return new ConversationFacade(creator, service, userFacade);
  }

}
