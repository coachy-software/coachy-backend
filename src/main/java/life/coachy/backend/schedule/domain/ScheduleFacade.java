package life.coachy.backend.schedule.domain;

import com.querydsl.core.types.Predicate;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import life.coachy.backend.infrastructure.constant.ApiLayers;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import life.coachy.backend.request.domain.RequestFacade;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateCommandDto;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.schedule.query.ScheduleQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ScheduleFacade {

  private final QueryOperationsFactory queryOperationsFactory;
  private final ScheduleQueryRepository queryDtoRepository;
  private final ScheduleCreator creator;
  private final ScheduleService service;
  private final NotificationFacade notificationFacade;
  private final RequestFacade requestFacade;
  private final UserFacade userFacade;

  public ScheduleFacade(ScheduleQueryRepository queryDtoRepository, QueryOperationsFactory queryOperationsFactory, ScheduleService service,
      ScheduleCreator creator, UserFacade userFacade, NotificationFacade notificationFacade, RequestFacade requestFacade) {
    this.queryDtoRepository = queryDtoRepository;
    this.queryOperationsFactory = queryOperationsFactory;
    this.service = service;
    this.creator = creator;
    this.userFacade = userFacade;
    this.notificationFacade = notificationFacade;
    this.requestFacade = requestFacade;
  }

  public List<ScheduleQueryDto> fetchAll(Predicate predicate, Pageable pageable) {
    return this.queryOperationsFactory.obtainOperation(predicate, pageable, this.queryDtoRepository);
  }

  public void delete(ObjectId id) {
    this.service.delete(this.userFacade, id);
  }

  public void update(ObjectId id, ScheduleUpdateCommandDto dto) {
    this.service.update(id, this.creator.from(dto));
  }

  public ScheduleQueryDto fetchOne(ObjectId id) {
    return this.service.fetchOne(id);
  }

  public URI create(ScheduleCreateCommandDto dto) {
    Schedule schedule = this.service.save(this.creator.from(dto));
    this.service.givePermissions(this.userFacade, schedule, dto);

    String token = this.requestFacade.createToken(dto.getCharge());
    this.sendAcknowledgeRequestNotification(dto.getCreator(), dto.getCharge(), token, schedule.identifier);

    return ServletUriComponentsBuilder.fromCurrentContextPath().path("/" + ApiLayers.SCHEDULES + "/{id}").buildAndExpand(schedule.identifier).toUri();
  }

  public void acceptSchedule(ObjectId id, String token) {
    this.requestFacade.invalidateToken(token, () -> "");

    ScheduleQueryDto queryDto = this.service.fetchOne(id);
    this.service.accept(queryDto, this.creator.from(queryDto));
  }

  public void rejectScheduleRequest(ObjectId id, String token) {
    this.service.throwIfAbsent(id);
    this.requestFacade.invalidateToken(token, () -> "");
  }

  private void sendAcknowledgeRequestNotification(ObjectId senderId, ObjectId recipientId, String requestToken, ObjectId scheduleId) {
    UserQueryDto senderQueryDto = this.userFacade.fetchOne(senderId);
    NotificationMessageDto dto = NotificationMessageDtoBuilder.create()
        .withSenderName(senderQueryDto.getUsername())
        .withSenderAvatar(senderQueryDto.getAvatar())
        .withSenderId(senderQueryDto.getIdentifier())
        .withType("SCHEDULE_REQUEST")
        .withContent(this.service.makeNotificationResponse(requestToken, scheduleId))
        .withRecipientId(recipientId)
        .withCreatedAt(LocalDateTime.now())
        .build();

    this.notificationFacade.sendNotificationToUser(dto);
  }

}
