package life.coachy.backend.old_exercise;

import static org.junit.jupiter.api.Assertions.*;

import life.coachy.backend.old_exercise.dto.ExerciseDto;
import life.coachy.backend.old_exercise.dto.ExerciseDtoBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExerciseDtoTest {

  private ExerciseDto dto = ExerciseDtoBuilder.createBuilder()
      .withName("testName123")
      .withSets(3)
      .withReps(15)
      .withMiniSets(4)
      .withTemplate(null)
      .build();

  @Test
  public void toEntityTest() {
    assertEquals("Exercise{"
        + "identifier=null, "
        + "name='testName123', "
        + "sets=3, "
        + "reps=15, "
        + "miniSets=4, "
        + "template=null}", String.valueOf(ExerciseMapper.INSTANCE.exerciseDtoToExercise(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName())
    );
  }

}
