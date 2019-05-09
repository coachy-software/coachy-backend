package life.coachy.backend.conversation.message;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Set;
import life.coachy.backend.conversation.message.domain.MessageFacade;
import life.coachy.backend.conversation.message.query.MessageQueryDto;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constants.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermissions;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.MESSAGES)
class MessageEndpoint {

  private final MessageFacade messageFacade;

  @Autowired
  MessageEndpoint(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @RequiresAuthenticated
  @RequiresPermissions("conversation.{id}.read")
  @ApiOperation("Displays all messages by conversation identifier")
  @GetMapping("{id}")
  ResponseEntity<Set<MessageQueryDto>> findAll(@ApiParam("Conversation identifier") @PathVariable ObjectId id) {
    return ResponseEntity.ok(this.messageFacade.findAllByConversationId(id));
  }

}
