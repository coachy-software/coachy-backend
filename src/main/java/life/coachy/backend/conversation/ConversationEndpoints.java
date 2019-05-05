package life.coachy.backend.conversation;

import life.coachy.backend.conversation.domain.ConversationFacade;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.CONVERSATIONS)
class ConversationEndpoints {

  private final ConversationFacade conversationFacade;

  public ConversationEndpoints(ConversationFacade conversationFacade) {
    this.conversationFacade = conversationFacade;
  }

}
