package life.coachy.backend.conversation.domain;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.conversation.query.ConversationQueryRepository;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
class ConversationService {

  private final ConversationRepository repository;
  private final ConversationQueryRepository queryRepository;

  @Autowired
  ConversationService(ConversationRepository repository, ConversationQueryRepository queryRepository) {
    this.repository = repository;
    this.queryRepository = queryRepository;
  }

  void update(ConversationQueryDto queryDto, Conversation conversation) {
    conversation.setIdentifier(queryDto.getIdentifier());
    conversation.setConversers(queryDto.getConversers());

    this.save(conversation);
  }

  ConversationQueryDto findOneOrThrow(List<String> conversers) {
    return this.findOne(conversers).orElseThrow(ConversationNotFoundException::new);
  }

  Optional<ConversationQueryDto> findOne(List<String> conversers) {
    return this.queryRepository.findByConversersContains(conversers);
  }

  Optional<ConversationQueryDto> findOne(ObjectId id) {
    return this.queryRepository.findById(id);
  }

  URI createIfAbsent(ConversationDto dto, Conversation conversation, Consumer<ConversationQueryDto> consumer) {
    Optional<ConversationQueryDto> queryDto = this.findOne(dto.getConversers());

    if (!queryDto.isPresent()) {
      this.save(conversation);
      ConversationQueryDto newQueryDto = this.findOne(dto.getConversers()).get();

      consumer.accept(newQueryDto);
      return this.makeLocationHeaderUri(newQueryDto.getIdentifier());
    }

    return this.makeLocationHeaderUri(queryDto.get().getIdentifier());
  }

  Page<ConversationQueryDto> findAll(Pageable pageable, List<String> conversers) {
    return this.queryRepository.findAllByConversersContainsOrderByLastMessageDateDesc(conversers, pageable);
  }

  private void save(Conversation conversation) {
    this.repository.save(conversation);
  }

  private URI makeLocationHeaderUri(ObjectId identifier) {
    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.CONVERSATIONS + "/{id}")
        .buildAndExpand(identifier)
        .toUri();
  }

}
