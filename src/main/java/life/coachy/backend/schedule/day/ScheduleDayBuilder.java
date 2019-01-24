package life.coachy.backend.schedule.day;

import java.util.List;
import life.coachy.backend.exercise.ExerciseDto;
import life.coachy.backend.util.Buildable;

class ScheduleDayBuilder implements Buildable<ScheduleDay> {

  String name;
  String musclesPart;
  List<ExerciseDto> exercises;
  boolean trainingDay;

  public ScheduleDayBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public ScheduleDayBuilder withMusclesPart(String musclesPart) {
    this.musclesPart = musclesPart;
    return this;
  }

  public ScheduleDayBuilder withExercises(List<ExerciseDto> exercises) {
    this.exercises = exercises;
    return this;
  }

  public ScheduleDayBuilder isTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
    return this;
  }

  @Override
  public ScheduleDay build() {
    return new ScheduleDay(this);
  }

}
