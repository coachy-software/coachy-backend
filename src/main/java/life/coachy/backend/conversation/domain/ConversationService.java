package life.coachy.backend.conversation.domain;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import life.coachy.backend.conversation.domain.dto.ConversationDto;
import life.coachy.backend.conversation.domain.exception.ConversationNotFoundException;
import life.coachy.backend.conversation.query.ConversationQueryDto;
import life.coachy.backend.conversation.query.ConversationQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ConversationService {

  private final ConversationRepository repository;
  private final ConversationQueryRepository queryRepository;

  @Autowired
  ConversationService(ConversationRepository repository, ConversationQueryRepository queryRepository) {
    this.repository = repository;
    this.queryRepository = queryRepository;
  }

  void save(Conversation conversation) {
    this.repository.save(conversation);
  }

  void update(ConversationQueryDto queryDto, Conversation conversation) {
    conversation.setIdentifier(queryDto.getIdentifier());
    conversation.setConversers(queryDto.getConversers());

    this.save(conversation);
  }

  ConversationQueryDto findOneOrThrow(List<String> conversers) {
    return this.findOne(conversers).orElseThrow(ConversationNotFoundException::new);
  }

  ConversationQueryDto createIfAbsent(ConversationDto dto, Conversation conversation, Consumer<ConversationQueryDto> consumer) {
    Optional<ConversationQueryDto> queryDto = this.findOne(dto.getConversers());

    if (!queryDto.isPresent()) {
      this.save(conversation);
      ConversationQueryDto newQueryDto = this.findOne(dto.getConversers()).get();
      consumer.accept(newQueryDto);
      return newQueryDto;
    }

    return queryDto.get();
  }

  Page<ConversationQueryDto> findAll(Pageable pageable, List<String> conversers) {
    return this.queryRepository.findAllByConversersContainsOrderByLastMessageDateDesc(conversers, pageable);
  }

  private Optional<ConversationQueryDto> findOne(List<String> conversers) {
    return this.queryRepository.findByConversersContains(conversers);
  }

}
