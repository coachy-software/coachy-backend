package life.coachy.backend.schedule.domain;

import life.coachy.backend.infrastructure.converter.PropertiesToMapConverter;
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto;
import life.coachy.backend.schedule.domain.exception.ScheduleNotFoundException;
import life.coachy.backend.schedule.query.ScheduleQueryDto;
import life.coachy.backend.schedule.query.ScheduleQueryDtoRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ScheduleService {

  private final ScheduleQueryDtoRepository queryDtoRepository;
  private final PropertiesToMapConverter propertiesToMapConverter;
  private final ScheduleRepository scheduleRepository;

  @Autowired
  public ScheduleService(ScheduleQueryDtoRepository queryDtoRepository, PropertiesToMapConverter propertiesToMapConverter, ScheduleRepository scheduleRepository) {
    this.queryDtoRepository = queryDtoRepository;
    this.propertiesToMapConverter = propertiesToMapConverter;
    this.scheduleRepository = scheduleRepository;
  }

  void save(Schedule schedule) {
    this.scheduleRepository.save(schedule);
  }

  ScheduleQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(ScheduleNotFoundException::new);
  }

  void delete(ObjectId id) {
    this.checkIfExists(id, () -> this.scheduleRepository.deleteById(id));
  }

  void convertPropertiesToMapAndSave(ObjectId id, ScheduleUpdateEntireEntityCommandDto dto) {
    this.checkIfExists(id, () -> this.scheduleRepository.updateEntireEntity(id, this.propertiesToMapConverter.convert(dto)));
  }

  private void checkIfExists(ObjectId id, Runnable runnable) {
    if (!this.queryDtoRepository.existsByIdentifier(id)) {
      throw new ScheduleNotFoundException();
    }
    runnable.run();
  }

}
