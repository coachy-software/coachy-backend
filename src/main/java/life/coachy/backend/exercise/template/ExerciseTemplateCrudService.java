package life.coachy.backend.exercise.template;

import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.util.CrudOperationsService;
import life.coachy.backend.util.dto.AbstractDto;
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
  public ExerciseTemplate save(ExerciseTemplate entity) {
    return this.repository.save(entity);
  }

  @Override
  public <S extends AbstractDto> ExerciseTemplate save(S dto) {
//    return this.repository.save(); TODO
    return null;
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
  public List<ExerciseTemplate> findAll(Predicate predicate) {
    return Lists.newArrayList(this.repository.findAll(predicate));
  }

  @Override
  public Page<ExerciseTemplate> findAll(Predicate predicate, Pageable pageable) {
    return this.repository.findAll(predicate, pageable);
  }

  @Override
  public Page<ExerciseTemplate> findAll(Pageable pageable) {
    return this.repository.findAll(pageable);
  }

}
