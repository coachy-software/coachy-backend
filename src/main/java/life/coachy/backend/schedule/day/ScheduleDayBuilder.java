package life.coachy.backend.schedule.day;

import java.util.Set;
import life.coachy.backend.exercise.domain.dto.ExerciseDto;
import life.coachy.backend.infrastructure.util.Buildable;

final class ScheduleDayBuilder implements Buildable<ScheduleDay> {

  String name;
  Set<ExerciseDto> exercises;
  boolean trainingDay;

  private ScheduleDayBuilder() {}

  public static ScheduleDayBuilder create() {
    return new ScheduleDayBuilder();
  }

  public ScheduleDayBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleDayBuilder withExercises(Set<ExerciseDto> exercises) {
    this.exercises = exercises;
    return this;
  }

  public ScheduleDayBuilder withTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
    return this;
  }

  @Override
  public ScheduleDay build() {
    return new ScheduleDay(this);
  }

}
