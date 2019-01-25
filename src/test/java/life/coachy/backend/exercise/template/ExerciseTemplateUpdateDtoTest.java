package life.coachy.backend.exercise.template;

import java.util.Arrays;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExerciseTemplateUpdateDtoTest {

  @Test
  public void toEntityTest() {
    ExerciseTemplateUpdateDto dto = new ExerciseTemplateUpdateDto("testName", Arrays.asList("test", "test2"),
        "Brief Description", true);

    Assertions.assertEquals("ExerciseTemplate{"
        + "identifier=null, "
        + "name='testName', "
        + "exampleImages=[test, test2], "
        + "briefDescription='Brief Description', "
        + "verified=true}", dto.toEntity().toString());
  }

}
