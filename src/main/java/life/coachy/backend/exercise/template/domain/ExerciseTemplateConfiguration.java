package life.coachy.backend.exercise.template.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ExerciseTemplateConfiguration {

  @Bean
  ExerciseTemplateFacade exerciseTemplateFacade(ExerciseTemplateCrudService service) {
    return new ExerciseTemplateFacade(service);
  }

}
