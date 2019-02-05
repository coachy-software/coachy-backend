package life.coachy.backend.exercise.template.dto;

import life.coachy.backend.exercise.template.ExerciseTemplateMapper;
import life.coachy.backend.util.dto.AbstractDtoMapperFactory;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

@Component
public class ExerciseTemplateDtoMapperFactory extends AbstractDtoMapperFactory<ExerciseTemplateMapper> {

  private final static Reflections REFLECTIONS = new Reflections("life.coachy.backend.exercise.template.dto");

  public ExerciseTemplateDtoMapperFactory() {
    super(ExerciseTemplateMapper.INSTANCE, REFLECTIONS);
  }

}
