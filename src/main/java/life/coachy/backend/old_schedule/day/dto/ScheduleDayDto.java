package life.coachy.backend.old_schedule.day.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.old_exercise.dto.ExerciseDto;
import life.coachy.backend.old_schedule.day.ScheduleDayMapper;
import life.coachy.backend.infrastructure.util.dto.AbstractDto;
import life.coachy.backend.infrastructure.util.dto.DataTransferObject;

@DataTransferObject(mapperClass = ScheduleDayMapper.class, entityName = "ScheduleDay")
public class ScheduleDayDto extends AbstractDto {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}")
  private List<ExerciseDto> exercises;
  private boolean trainingDay;

  ScheduleDayDto(ScheduleDayDtoBuilder builder) {
    this.name = builder.name;
    this.exercises = builder.exercises;
    this.trainingDay = builder.trainingDay;
  }

  ScheduleDayDto() {}

  public String getName() {
    return this.name;
  }

  public List<ExerciseDto> getExercises() {
    return this.exercises;
  }

  public boolean isTrainingDay() {
    return this.trainingDay;
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

}
