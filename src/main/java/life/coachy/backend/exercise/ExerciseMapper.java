package life.coachy.backend.exercise;

import life.coachy.backend.exercise.dto.ExerciseDto;
import life.coachy.backend.util.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper extends MapStructMapper {

  ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);

  Exercise exerciseDtoToExercise(ExerciseDto dto);

}
