package life.coachy.backend.schedule.day;

import java.util.List;
import life.coachy.backend.exercise.ExerciseDto;
import life.coachy.backend.util.IdentifiableEntity;
import org.springframework.data.annotation.Id;

class ScheduleDay implements IdentifiableEntity<String> {

  @Id
  private String name;
  private String musclesPart;
  private List<ExerciseDto> exercises;
  private boolean trainingDay;

  ScheduleDay(ScheduleDayBuilder builder) {
    this.name = builder.name;
    this.musclesPart = builder.musclesPart;
    this.exercises = builder.exercises;
    this.trainingDay = builder.trainingDay;
  }

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

  public String getMusclesPart() {
    return this.musclesPart;
  }

  public void setMusclesPart(String musclesPart) {
    this.musclesPart = musclesPart;
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

}
