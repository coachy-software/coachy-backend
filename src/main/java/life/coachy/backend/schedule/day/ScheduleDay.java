package life.coachy.backend.schedule.day;

import java.util.Set;
import life.coachy.backend.exercise.domain.dto.ExerciseDto;
import org.springframework.data.annotation.Id;

class ScheduleDay {

  @Id private String name;
  private Set<ExerciseDto> exercises;
  private boolean trainingDay;

  ScheduleDay(ScheduleDayBuilder builder) {
    this.name = builder.name;
    this.exercises = builder.exercises;
    this.trainingDay = builder.trainingDay;
  }

  ScheduleDay() {}

  public static ScheduleDayBuilder builder() {
    return ScheduleDayBuilder.create();
  }

}
