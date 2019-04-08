package life.coachy.backend.schedule.day;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import life.coachy.backend.exercise.dto.ExerciseDto;
import life.coachy.backend.infrastructure.command.CommandDtoMarker;

public class ScheduleDayDto implements CommandDtoMarker {

  @NotNull private String name;
  @NotNull private LinkedHashSet<ExerciseDto> exercises;
  @NotNull private boolean trainingDay;

  public ScheduleDayDto(String name, LinkedHashSet<ExerciseDto> exercises, boolean trainingDay) {
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
