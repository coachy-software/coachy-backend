package life.coachy.backend.conversation.domain;

import com.google.common.collect.Lists;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.dto.ConversationUpdateCommandDto;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
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

  public void updateLastMesasge(ObjectId id, ConversationUpdateCommandDto dto) {
    ConversationQueryDto queryDto = this.fetchOne(id);
    this.service.update(queryDto, this.creator.from(dto));
  }

  public <T> T checkIfExists(ConversationDto conversationDto, Function<URI, T> existHandler, Function<URI, T> createHandler) {
    boolean isPresent = this.service.findOne(conversationDto.getConversers()).isPresent();
    URI conversationUri = this.createIfAbsent(conversationDto);

    return isPresent ? existHandler.apply(conversationUri) : createHandler.apply(conversationUri);
  }

  public URI createIfAbsent(ConversationDto dto) {
    dto.getConversers().forEach(this.userFacade::checkIfExists);

    return this.service.createIfAbsent(dto, this.creator.from(dto), (queryDto) -> {
      dto.getConversers().forEach(converser -> {
        this.userFacade.givePermissions(converser, "conversation." + queryDto.getIdentifier() + ".read");
        this.userFacade.givePermissions(converser, "conversation." + queryDto.getIdentifier() + ".read");
      });
    });
  }

  public ConversationQueryDto fetchOne(ObjectId id) {
    return this.service.findOne(id).orElseThrow(ConversationNotFoundException::new);
  }

}
