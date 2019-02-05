package life.coachy.backend.schedule.day.dto;

import java.util.List;
import life.coachy.backend.exercise.ExerciseDto;

public final class ScheduleDayDtoBuilder {

  String name;
  String musclesPart;
  List<ExerciseDto> exercises;
  boolean trainingDay;

  private ScheduleDayDtoBuilder() {}

  public static ScheduleDayDtoBuilder createBuilder() {
    return new ScheduleDayDtoBuilder();
  }

  public ScheduleDayDtoBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleDayDtoBuilder withMusclesPart(String musclesPart) {
    this.musclesPart = musclesPart;
    return this;
  }

  public ScheduleDayDtoBuilder withExercises(List<ExerciseDto> exercises) {
    this.exercises = exercises;
    return this;
  }

  public ScheduleDayDtoBuilder withTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
    return this;
  }

  public ScheduleDayDto build() {
    return new ScheduleDayDto(this);
  }

}
