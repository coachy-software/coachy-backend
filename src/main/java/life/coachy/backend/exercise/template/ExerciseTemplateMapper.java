package life.coachy.backend.exercise.template;

import life.coachy.backend.exercise.template.dto.ExerciseTemplateDto;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateUpdateDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseTemplateMapper extends MapStructMapper {

  ExerciseTemplateMapper INSTANCE = Mappers.getMapper(ExerciseTemplateMapper.class);

  @Mapping(target = "identifier", source = "dto.identifier")
  ExerciseTemplate exerciseTemplateDtoToExerciseTemplate(ExerciseTemplateDto dto);

  ExerciseTemplate exerciseTemplateUpdateDtoToExerciseTemplate(ExerciseTemplateUpdateDto dto);

}
