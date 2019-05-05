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
    this.userFacade.ifExists(id);
    return this.service.findAllByRecipientOrSender(id, pageable);
  }

  public void create(ConversationDto dto) {
    this.service.save(this.creator.from(dto));
  }

  public void updateLastMesasge(ConversationUpdateCommandDto dto) {
    ConversationQueryDto queryDto = this.service.findOneOrThrow(dto.getIdentifier());
    this.userFacade.ifExists(dto.getIdentifier(), () -> this.service.update(queryDto, this.creator.from(dto)));
  }

}
