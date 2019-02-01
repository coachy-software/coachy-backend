package life.coachy.backend.exercise.template;

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
class ExerciseTemplateCrudService implements CrudOperationsService<ExerciseTemplate, ObjectId> {

  private ExerciseTemplateRepository repository;

  @Autowired
  ExerciseTemplateCrudService(ExerciseTemplateRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<ExerciseTemplate> findByName(String name) {
    return this.repository.findByName(name);
  }

  @Override
  public Optional<ExerciseTemplate> findById(ObjectId objectId) {
    return this.repository.findById(objectId);
  }

  @Override
  public List<ExerciseTemplate> findAll() {
    return this.repository.findAll();
  }

  @Override
  public <S extends ExerciseTemplate> S save(S entity) {
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

  // todo

  @Override
  public List<ExerciseTemplate> findAll(Predicate predicate) {
    return null;
  }

  @Override
  public Page<ExerciseTemplate> findAll(Predicate predicate, Pageable pageable) {
    return null;
  }

  @Override
  public Page<ExerciseTemplate> findAll(Pageable pageable) {
    return null;
  }

}
