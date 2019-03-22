package life.coachy.backend.exercise;

import life.coachy.backend.exercise.dto.ExerciseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper {

  ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

  Exercise exerciseDtoToExercise(ExerciseDto dto);

}
