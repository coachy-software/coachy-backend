package life.coachy.backend.conversation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import life.coachy.backend.conversation.domain.ConversationFacade;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.permission.RequiresPermission;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.CONVERSATIONS)
class ConversationEndpoint {

  private final ConversationFacade conversationFacade;

  public ConversationEndpoint(ConversationFacade conversationFacade) {
    this.conversationFacade = conversationFacade;
  }

  @ApiOperation("Displays all user's conversations by recipient or sender identifier")
  @RequiresAuthenticated
  @RequiresPermission("user.{id}.read")
  @GetMapping("by-converser/{id}")
  ResponseEntity<List<ConversationQueryDto>> fetchAll(@PathVariable @ApiParam("Recipient or sender id") ObjectId id, Pageable pageable) {
    return ResponseEntity.ok(this.conversationFacade.fetchAll(id, pageable).getContent());
  }

  @ApiOperation("Displays the conversation's details")
  @RequiresAuthenticated
  @RequiresPermission("conversation.{id}.read")
  @GetMapping("{id}")
  public ResponseEntity<ConversationQueryDto> fetchOne(@PathVariable @ApiParam("Conversation id") ObjectId id) {
    return ResponseEntity.ok(this.conversationFacade.fetchOne(id));
  }

  @RequiresAuthenticated
  @ApiOperation("Creates a conversation if absent, otherwise refers to existing one")
  @PostMapping
  ResponseEntity<?> create(@RequestBody ConversationDto dto) {
    return this.conversationFacade.create(dto,
        uri -> ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri),
        uri -> ResponseEntity.status(HttpStatus.CREATED).location(uri)
    ).build();
  }

}
