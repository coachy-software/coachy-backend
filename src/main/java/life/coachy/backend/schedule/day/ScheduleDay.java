package life.coachy.backend.schedule.day;

import java.util.List;
import life.coachy.backend.old_exercise.dto.ExerciseDto;
import org.springframework.data.annotation.Id;

class ScheduleDay implements IdentifiableEntity<String> {

  @Id
  private String name;
  private List<ExerciseDto> exercises;
  private boolean trainingDay;

  ScheduleDay(ScheduleDayBuilder builder) {
    this.name = builder.name;
    this.exercises = builder.exercises;
    this.trainingDay = builder.trainingDay;
  }

  ScheduleDay() {}

  @Override
  public String getIdentifier() {
    return this.name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ExerciseDto> getExercises() {
    return this.exercises;
  }

  public void setExercises(List<ExerciseDto> exercises) {
    this.exercises = exercises;
  }

  public boolean isTrainingDay() {
    return this.trainingDay;
  }

  public void setTrainingDay(boolean trainingDay) {
    this.trainingDay = trainingDay;
  }

  @Override
  public String toString() {
    return "ScheduleDay{" +
        "name='" + this.name + '\'' +
        ", exercises=" + this.exercises +
        ", trainingDay=" + this.trainingDay +
        '}';
  }

}
