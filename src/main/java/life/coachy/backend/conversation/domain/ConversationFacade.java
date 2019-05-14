package life.coachy.backend.conversation.domain;

import com.google.common.collect.Lists;
import java.util.List;
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

  public Page<ConversationQueryDto> fetchAll(ObjectId id, Pageable pageable) {
    return this.service.findAll(pageable, Lists.newArrayList(this.userFacade.fetchOne(id).getUsername()));
  }

  public void updateLastMesasge(ConversationDto conversationDto, ConversationUpdateCommandDto dto) {
    ConversationQueryDto queryDto = this.service.findOneOrThrow(conversationDto.getConversers());
    this.service.update(queryDto, this.creator.from(dto));
  }

  public ConversationQueryDto createIfAbsent(ConversationDto dto) {
    return this.service.createIfAbsent(dto, this.creator.from(dto), (queryDto) -> {
      dto.getConversers().forEach(converser -> {
        this.userFacade.givePermissions(converser, "conversation." + queryDto.getIdentifier() + ".read");
        this.userFacade.givePermissions(converser, "conversation." + queryDto.getIdentifier() + ".read");
      });
    });
  }

}
