package life.coachy.backend.conversation.message.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MessageConfiguration {

  @Bean
  MessageFacade messageFacade(MessageService messageService) {
    MessageCreator messageCreator = new MessageCreator();
    return new MessageFacade(messageService, messageCreator);
  }

}
