package life.coachy.backend.exercise.template;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateDtoMapperFactory;
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
  private ExerciseTemplateDtoMapperFactory mapperFactory;

  @Autowired
  public ExerciseTemplateCrudService(ExerciseTemplateRepository repository,
      ExerciseTemplateDtoMapperFactory mapperFactory) {
    this.repository = repository;
    this.mapperFactory = mapperFactory;
  }

  @Override
  public Optional<ExerciseTemplate> findByName(String name) {
    Preconditions.checkNotNull(name, "Exercise template entity name cannot be null");
    return this.repository.findByName(name);
  }

  @Override
  public Optional<ExerciseTemplate> findById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Exercise template entity identifier cannot be null");
    return this.repository.findById(objectId);
  }

  @Override
  public List<ExerciseTemplate> findAll() {
    return this.repository.findAll();
  }

  @Override
  public ExerciseTemplate save(ExerciseTemplate entity) {
    Preconditions.checkNotNull(entity, "Exercise template entity cannot be null");
    return this.repository.save(entity);
  }

  @Override
  public <S extends AbstractDto> ExerciseTemplate save(S dto) {
    Preconditions.checkNotNull(dto, "Exercsie template DTO cannot be null");
    return this.repository.save(this.mapperFactory.obtainEntity(dto));
  }

  @Override
  public void deleteById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Exercise template entity identifier cannot be null");
    this.repository.deleteById(objectId);
  }

  @Override
  public boolean existsById(ObjectId objectId) {
    Preconditions.checkNotNull(objectId, "Exercise template entity identifier cannot be null");
    return this.repository.existsById(objectId);
  }

  @Override
  public List<ExerciseTemplate> findAll(Predicate predicate) {
    Preconditions.checkNotNull(predicate);
    return Lists.newArrayList(this.repository.findAll(predicate));
  }

  @Override
  public Page<ExerciseTemplate> findAll(Predicate predicate, Pageable pageable) {
    Preconditions.checkNotNull(predicate);
    Preconditions.checkNotNull(pageable);

    return this.repository.findAll(predicate, pageable);
  }

  @Override
  public Page<ExerciseTemplate> findAll(Pageable pageable) {
    Preconditions.checkNotNull(pageable);
    return this.repository.findAll(pageable);
  }

}
