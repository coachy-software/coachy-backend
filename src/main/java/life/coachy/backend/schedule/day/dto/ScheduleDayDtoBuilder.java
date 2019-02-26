package life.coachy.backend.schedule.day.dto;

import java.util.List;
import life.coachy.backend.exercise.dto.ExerciseDto;
import life.coachy.backend.util.Buildable;

public final class ScheduleDayDtoBuilder implements Buildable<ScheduleDayDto> {

  String name;
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

  public ScheduleDayDtoBuilder withExercises(List<ExerciseDto> exercises) {
    this.exercises = exercises;
    return this;
  }

  public ScheduleDayDtoBuilder withTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
    return this;
  }

  @Override
  public ScheduleDayDto build() {
    return new ScheduleDayDto(this);
  }

}
