package life.coachy.backend.schedule;

import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
class ScheduleCrudService implements CrudOperationsService<Schedule, ObjectId> {

  private ScheduleRepository repository;

  @Autowired
  ScheduleCrudService(ScheduleRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Schedule> findByName(String name) {
    return this.repository.findByName(name);
  }

  @Override
  public Optional<Schedule> findById(ObjectId objectId) {
    return this.repository.findById(objectId);
  }

  @Override
  public List<Schedule> findAll() {
    return this.repository.findAll();
  }

  @Override
  public <S extends Schedule> S save(S entity) {
    return this.repository.save(entity);
  }

  @Override
  public void deleteById(ObjectId objectId) {
    this.repository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    return this.repository.existsById(objectId);
  }

  @Override
  public List<Schedule> findAll(Predicate predicate) {
    return this.repository.findAll();
  }

  @Override
  public Page<Schedule> findAll(Predicate predicate, Pageable pageable) {
    return null;
  }

}
