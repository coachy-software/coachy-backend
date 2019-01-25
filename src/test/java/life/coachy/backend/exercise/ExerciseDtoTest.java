package life.coachy.backend.exercise;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExerciseDtoTest {

  @Test
  public void toEntityTest() {
    ExerciseDto dto = new ExerciseDto("testName", 3, 15, 4, null);
    Assertions.assertEquals("Exercise{name='testName', sets=3, reps=15, miniSets=4, template=null}",
        dto.toEntity().toString());
  }

}
