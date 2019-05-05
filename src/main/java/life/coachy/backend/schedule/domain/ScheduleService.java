package life.coachy.backend.schedule.domain;

import java.util.function.Consumer;
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

  @Autowired
  public ScheduleService(ScheduleQueryRepository queryDtoRepository, ScheduleRepository scheduleRepository) {
    this.queryDtoRepository = queryDtoRepository;
    this.scheduleRepository = scheduleRepository;
  }

  Schedule save(Schedule schedule) {
    return this.scheduleRepository.save(schedule);
  }

  ScheduleQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
  }

  void delete(UserFacade userFacade, ObjectId id) {
    this.ifExists(id, (queryDto) -> {
      this.scheduleRepository.deleteById(id);
      userFacade.nullifyPermissions(queryDto.getCharge(), id);
      userFacade.nullifyPermissions(queryDto.getCreator(), id);
    });
  }

  void givePermissions(UserFacade userFacade, Schedule schedule, ScheduleCreateCommandDto dto) {
    userFacade.givePermissions(dto.getCharge(), "schedule." + schedule.identifier + ".read");
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

  private void ifExists(ObjectId id, Consumer<ScheduleQueryDto> consumer) {
    ScheduleQueryDto queryDto = this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
    consumer.accept(queryDto);
  }

}
