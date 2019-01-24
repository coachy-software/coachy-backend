package life.coachy.backend.schedule.day;

import java.util.List;
import life.coachy.backend.exercise.ExerciseDto;
import life.coachy.backend.util.AbstractDto;

public class ScheduleDayDto extends AbstractDto<ScheduleDay> {

  private String name;
  private String musclesPart;
  private List<ExerciseDto> exercises;
  private boolean trainingDay;

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
