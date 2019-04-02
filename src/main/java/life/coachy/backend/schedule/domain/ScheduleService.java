package life.coachy.backend.schedule.domain;

import java.util.function.Consumer;
import life.coachy.backend.infrastructure.converter.PropertiesToMapConverter;
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
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
  private final PropertiesToMapConverter propertiesToMapConverter;
  private final ScheduleRepository scheduleRepository;

  @Autowired
  public ScheduleService(ScheduleQueryRepository queryDtoRepository, PropertiesToMapConverter propertiesConverter, ScheduleRepository scheduleRepository) {
    this.queryDtoRepository = queryDtoRepository;
    this.propertiesToMapConverter = propertiesConverter;
    this.scheduleRepository = scheduleRepository;
  }

  Schedule save(Schedule schedule) {
    return this.scheduleRepository.save(schedule);
  }

  ScheduleQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
  }

  void delete(UserFacade userFacade, ObjectId id) {
    this.checkIfExists(id, (queryDto) -> {
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

  void convertPropertiesToMapAndSave(ObjectId id, ScheduleUpdateEntireEntityCommandDto dto) {
    this.checkIfExists(id, (queryDto) -> this.scheduleRepository.updateEntireEntity(id, this.propertiesToMapConverter.convert(dto)));
  }

  private void checkIfExists(ObjectId id, Consumer<ScheduleQueryDto> consumer) {
    ScheduleQueryDto queryDto = this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
    consumer.accept(queryDto);
  }

}
