package life.coachy.backend.schedule;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.schedule.dto.ScheduleDtoMapperFactory;
import life.coachy.backend.util.CrudOperationsService;
import life.coachy.backend.util.dto.AbstractDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ScheduleCrudService implements CrudOperationsService<Schedule, ObjectId> {

  private ScheduleRepository repository;
  private ScheduleDtoMapperFactory mapperFactory;

  @Autowired
  ScheduleCrudService(ScheduleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Schedule> findByName(String name) {
    Preconditions.checkNotNull(name, "Schedule entity name cannot be null");
    return this.repository.findByName(name);
  }

  @Override
  public Optional<Schedule> findById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");
    return this.repository.findById(objectId);
  }

  @Override
  public List<Schedule> findAll() {
    return this.repository.findAll();
  }

  public Schedule save(Schedule entity) {
    Preconditions.checkNotNull(entity, "Schedule entity cannot be null");
    return this.repository.save(entity);
  }

  @Override
  public <S extends AbstractDto> Schedule save(S dto) {
    Preconditions.checkNotNull(dto, "Schedule DTO cannot be null");
    return this.repository.save(this.mapperFactory.obtainEntity(dto));
  }

  @Override
  public void deleteById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");
    this.repository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Schedule entity identifier cannot be null");
    return this.repository.existsById(objectId);
  }

  @Override
  public List<Schedule> findAll(Predicate predicate) {
    Preconditions.checkNotNull(predicate);
    return Lists.newArrayList(this.repository.findAll(predicate));
  }

  @Override
  public Page<Schedule> findAll(Predicate predicate, Pageable pageable) {
    Preconditions.checkNotNull(predicate);
    Preconditions.checkNotNull(pageable);
    return this.repository.findAll(predicate, pageable);
  }

  @Override
  public Page<Schedule> findAll(Pageable pageable) {
    Preconditions.checkNotNull(pageable);
    return this.repository.findAll(pageable);
  }

}
