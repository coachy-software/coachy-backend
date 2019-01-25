package life.coachy.backend.schedule.day;

import java.util.Collections;
import java.util.List;
import life.coachy.backend.exercise.ExerciseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleDayBuilderTest {

  private List<ExerciseDto> exercises = Collections.singletonList(new ExerciseDto());
  private ScheduleDay scheduleDay = new ScheduleDayBuilder()
      .withName("test123")
      .withMusclesPart("test123")
      .withExercises(this.exercises)
      .isTrainingDay(true)
      .build();

  @Test
  public void shouldNotBeNull() {
    assertNotNull(this.scheduleDay);
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.scheduleDay.getName()),
        () -> assertNotNull(this.scheduleDay.getMusclesPart()),
        () -> assertNotNull(this.scheduleDay.getExercises())
    );
  }

  @Test
  public void toStringTest() {
    assertEquals("ScheduleDay{"
        + "name='test123', "
        + "musclesPart='test123', "
        + "exercises="+ this.exercises +", "
        + "trainingDay=true}", this.scheduleDay.toString());
  }

}
