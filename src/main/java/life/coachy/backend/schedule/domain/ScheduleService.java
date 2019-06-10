package life.coachy.backend.schedule.domain;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.exception.ScheduleNotFoundException;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.schedule.query.ScheduleQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ScheduleService {

  private final ScheduleQueryRepository queryDtoRepository;
  private final ScheduleRepository scheduleRepository;
  private final ObjectToJsonConverter toJsonConverter;

  @Autowired
  ScheduleService(ScheduleQueryRepository queryDtoRepository, ScheduleRepository scheduleRepository, ObjectToJsonConverter toJsonConverter) {
    this.queryDtoRepository = queryDtoRepository;
    this.scheduleRepository = scheduleRepository;
    this.toJsonConverter = toJsonConverter;
  }

  Schedule save(Schedule schedule) {
    return this.scheduleRepository.save(schedule);
  }

  ScheduleQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
  }

  void throwIfAbsent(ObjectId id) {
    if (!this.queryDtoRepository.existsByIdentifier(id)) {
      throw new ScheduleNotFoundException();
    }
  }

  void delete(UserFacade userFacade, ObjectId id) {
    this.ifExists(id, (queryDto) -> {
      this.scheduleRepository.deleteById(id);
      userFacade.nullifyPermissions(queryDto.getCharge(), id);
      userFacade.nullifyPermissions(queryDto.getCreator(), id);
    });
  }

  void givePermissions(UserFacade userFacade, Schedule schedule, ScheduleCreateCommandDto dto) {
    userFacade.givePermissions(dto.getCharge(),
        "schedule." + schedule.identifier + ".read",
        "schedule." + schedule.identifier + ".accept");
    userFacade.givePermissions(dto.getCreator(),
        "schedule." + schedule.identifier + ".read",
        "schedule." + schedule.identifier + ".update",
        "schedule." + schedule.identifier + ".delete"
    );
  }

  void update(ObjectId id, Schedule schedule) {
    this.ifExists(id, queryDto -> {
      schedule.setIdentifier(queryDto.getIdentifier());
      schedule.setCharge(queryDto.getCharge());
      schedule.setCreator(queryDto.getCreator());
      schedule.setCreatedAt(queryDto.getCreatedAt());
      schedule.setUpdatedAt(queryDto.getUpdatedAt());

      this.scheduleRepository.save(schedule);
    });
  }

  void accept(ScheduleQueryDto dto, Schedule schedule) {
    schedule.setIdentifier(dto.getIdentifier());
    schedule.setCreatedAt(dto.getCreatedAt());

    schedule.setUpdatedAt(dto.getUpdatedAt());
    schedule.setAccepted(true);

    this.scheduleRepository.save(schedule);
  }

  String makeNotificationResponse(String token, ObjectId scheduleId) {
    Map<String, String> responseContent = Maps.newHashMap(new HashMap<String, String>() {{
      this.put("token", token);
      this.put("scheduleId", scheduleId.toHexString());
    }});

    return this.toJsonConverter.convert(responseContent);
  }

  private void ifExists(ObjectId id, Consumer<ScheduleQueryDto> consumer) {
    ScheduleQueryDto queryDto = this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
    consumer.accept(queryDto);
  }

}
