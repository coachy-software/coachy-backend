package life.coachy.backend.conversation.domain;

import java.util.Set;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;

public class ConversationFacade {

  private final ConversationCreator creator;
  private final ConversationService service;
  private final UserFacade userFacade;

  public ConversationFacade(ConversationCreator creator, ConversationService service, UserFacade userFacade) {
    this.creator = creator;
    this.service = service;
    this.userFacade = userFacade;
  }

  public Set<ConversationQueryDto> fetchAllByRecipientOrSender(ObjectId id) {
    this.userFacade.ifExists(id);
    return this.service.findAllByRecipientOrSender(id);
  }

  public void create(ConversationDto dto) {
    this.service.save(this.creator.from(dto));
  }

  public void updateLastMesasge(ConversationUpdateCommandDto dto) {
    ConversationQueryDto queryDto = this.service.findOneOrThrow(dto.getIdentifier());
    this.userFacade.ifExists(dto.getIdentifier(), () -> this.service.update(queryDto, this.creator.from(dto)));
  }

}
