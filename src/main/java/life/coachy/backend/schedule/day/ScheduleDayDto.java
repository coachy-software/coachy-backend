package life.coachy.backend.schedule.day;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.ExerciseDto;
import life.coachy.backend.util.AbstractDto;

public class ScheduleDayDto extends AbstractDto<ScheduleDay> {

  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String name;
  @NotNull(message = "{notNull}") @NotEmpty(message = "{notEmpty}")
  private String musclesPart;
  @NotNull(message = "{notNull}")
  private List<ExerciseDto> exercises;
  private boolean trainingDay;

  public ScheduleDayDto(String name, String musclesPart, List<ExerciseDto> exercises, boolean trainingDay) {
    this.name = name;
    this.musclesPart = musclesPart;
    this.exercises = exercises;
    this.trainingDay = trainingDay;
  }

  public ScheduleDayDto() {
  }

  @Override
  public String getEntityName() {
    return this.name;
  }

  public String getMusclesPart() {
    return this.musclesPart;
  }

  public List<ExerciseDto> getExercises() {
    return this.exercises;
  }

  public boolean isTrainingDay() {
    return this.trainingDay;
  }

  @Override
  public ScheduleDay toEntity() {
    return new ScheduleDayBuilder()
        .withName(this.name)
        .withMusclesPart(this.musclesPart)
        .withExercises(this.exercises)
        .isTrainingDay(this.trainingDay)
        .build();
  }

}
