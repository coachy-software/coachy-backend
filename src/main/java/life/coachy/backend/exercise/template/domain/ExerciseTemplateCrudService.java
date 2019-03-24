package life.coachy.backend.exercise.template.domain;

import life.coachy.backend.exercise.template.domain.exception.ExerciseTemplateNotFoundException;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDto;
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDtoRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ExerciseTemplateCrudService {

  private final ExerciseTemplateQueryDtoRepository queryDtoRepository;

  @Autowired
  public ExerciseTemplateCrudService(ExerciseTemplateQueryDtoRepository queryDtoRepository) {
    this.queryDtoRepository = queryDtoRepository;
  }

  ExerciseTemplateQueryDto fetchOne(ObjectId id) {
    return this.queryDtoRepository.findById(id).orElseThrow(ExerciseTemplateNotFoundException::new);
  }

}
