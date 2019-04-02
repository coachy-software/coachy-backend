package life.coachy.backend.schedule.day.dto;

import java.util.Set;
import life.coachy.backend.exercise.domain.dto.ExerciseDto;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class ScheduleDayDto implements CommandDtoMarker {

  private String name;
  private Set<ExerciseDto> exercises;
  private boolean trainingDay;

  public ScheduleDayDto(String name, Set<ExerciseDto> exercises, boolean trainingDay) {
    this.name = name;
    this.exercises = exercises;
    this.trainingDay = trainingDay;
  }

  public String getName() {
    return this.name;
  }

  public Set<ExerciseDto> getExercises() {
    return this.exercises;
  }

  public boolean isTrainingDay() {
    return this.trainingDay;
  }

}
