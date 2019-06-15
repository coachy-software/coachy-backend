package life.coachy.backend.headway.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import life.coachy.backend.headway.domain.exception.HeadwayNotFoundException;
import life.coachy.backend.headway.query.HeadwayQueryDto;
import life.coachy.backend.headway.query.HeadwayQueryRepository;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class HeadwayService {

  private final HeadwayRepository headwayRepository;
  private final HeadwayQueryRepository headwayQueryRepository;
  private final ObjectToJsonConverter toJsonConverter;

  @Autowired
  HeadwayService(HeadwayRepository headwayRepository, HeadwayQueryRepository headwayQueryRepository, ObjectToJsonConverter toJsonConverter) {
    this.headwayRepository = headwayRepository;
    this.headwayQueryRepository = headwayQueryRepository;
    this.toJsonConverter = toJsonConverter;
  }

  Headway save(Headway headway) {
    return this.headwayRepository.save(headway);
  }

  void deleteById(ObjectId id) {
    if (!this.headwayQueryRepository.existsByIdentifier(id)) {
      throw new HeadwayNotFoundException();
    }

    this.headwayRepository.deleteById(id);
  }

  HeadwayQueryDto fetchOne(ObjectId id) {
    return this.headwayQueryRepository.findById(id).orElseThrow(HeadwayNotFoundException::new);
  }

  Set<HeadwayQueryDto> fetchAllByOwnerId(ObjectId id) {
    return this.headwayQueryRepository.findAllByOwnerIdOrderByCreatedAtDesc(id);
  }

  NotificationMessageDto makeNotificationMessage(UserQueryDto sender, UserQueryDto recipient, ObjectId headwayId) {
    return NotificationMessageDtoBuilder.create()
        .withSenderName(sender.getUsername())
        .withSenderAvatar(sender.getAvatar())
        .withSenderId(sender.getIdentifier())
        .withType("ALERT")
        .withContent(this.toJsonConverter.convert(Collections.singletonMap("link", "/headways/" + headwayId)))
        .withRecipientId(recipient.getIdentifier())
        .withCreatedAt(LocalDateTime.now())
        .build();
  }

}
