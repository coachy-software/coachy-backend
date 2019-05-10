package life.coachy.backend.conversation.domain;

import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ConversationFacade {

  private final ConversationCreator creator;
  private final ConversationService service;
  private final UserFacade userFacade;

  public ConversationFacade(ConversationCreator creator, ConversationService service, UserFacade userFacade) {
    this.creator = creator;
    this.service = service;
    this.userFacade = userFacade;
  }

  public Page<ConversationQueryDto> fetchAllByRecipientOrSender(ObjectId id, Pageable pageable) {
    return this.service.findAllByRecipientOrSender(this.userFacade.fetchOne(id).getUsername(), pageable);
  }

  public void updateLastMesasge(ConversationDto conversationDto, ConversationUpdateCommandDto dto) {
    ConversationQueryDto queryDto = this.service.findOneOrThrow(conversationDto.getRecipientName(), conversationDto.getSenderName());
    this.service.update(queryDto, this.creator.from(dto));
  }

  public void createIfAbsent(ConversationDto dto) {
    this.service.createIfAbsent(dto, this.creator.from(dto), () -> {
      this.userFacade.givePermissions(dto.getRecipientName(), "conversation." + dto.getIdentifier() + ".read");
      this.userFacade.givePermissions(dto.getSenderName(), "conversation." + dto.getIdentifier() + ".read");
    });
  }

}
