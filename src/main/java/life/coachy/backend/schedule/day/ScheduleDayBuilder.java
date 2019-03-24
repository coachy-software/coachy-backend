package life.coachy.backend.schedule.day;

import java.util.List;
import life.coachy.backend.old_exercise.dto.ExerciseDto;
import life.coachy.backend.infrastructure.util.Buildable;

class ScheduleDayBuilder implements Buildable<ScheduleDay> {

  String name;
  List<ExerciseDto> exercises;
  boolean trainingDay;

  private ScheduleDayBuilder() {}

  public static ScheduleDayBuilder createBuilder() {
    return new ScheduleDayBuilder();
  }

  ScheduleDayBuilder withName(String name) {
    this.name = name;
    return this;
  }

  ScheduleDayBuilder withExercises(List<ExerciseDto> exercises) {
    this.exercises = exercises;
    return this;
  }

  ScheduleDayBuilder isTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
    return this;
  }

  @Override
  public ScheduleDay build() {
    return new ScheduleDay(this);
  }

}
