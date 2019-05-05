package life.coachy.backend.conversation;

import java.util.List;
import life.coachy.backend.conversation.domain.ConversationFacade;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.CONVERSATIONS)
class ConversationEndpoints {

  private final ConversationFacade conversationFacade;

  public ConversationEndpoints(ConversationFacade conversationFacade) {
    this.conversationFacade = conversationFacade;
  }

  @GetMapping("{id}")
  public ResponseEntity<List<ConversationQueryDto>> fetchAll(@PathVariable ObjectId id, Pageable pageable) {
    return ResponseEntity.ok(this.conversationFacade.fetchAllByRecipientOrSender(id, pageable).getContent());
  }

}
