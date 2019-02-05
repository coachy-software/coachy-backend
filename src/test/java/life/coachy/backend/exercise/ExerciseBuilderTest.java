package life.coachy.backend.exercise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ExerciseBuilderTest {

  private Exercise exercise = ExerciseBuilder.createBuilder()
      .withName("testName")
      .withReps(15)
      .withSets(3)
      .withMiniSets(4)
      .build();

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.exercise),
        () -> assertNotNull(this.exercise.getName())
    );
  }

  @Test
  public void toStringTest() {
    assertEquals("Exercise{name='testName', sets=3, reps=15, miniSets=4, template=null}", this.exercise.toString());
  }

}
