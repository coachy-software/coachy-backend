package life.coachy.backend.exercise.template;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateUpdateDto;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateUpdateDtoBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExerciseTemplateUpdateDtoTest {

  private ExerciseTemplateUpdateDto dto = ExerciseTemplateUpdateDtoBuilder.createBuilder()
      .withName("testName123")
      .withBriefDescription("brief")
      .withExampleImages(Lists.newArrayList("example1", "example2"))
      .build();

  @Test
  public void toEntityTest() {
    assertEquals("ExerciseTemplate{"
            + "identifier=null, "
            + "name='testName123', "
            + "exampleImages=[example1, example2], "
            + "briefDescription='brief', "
            + "muscleGroup='null'}",
        String.valueOf(ExerciseTemplateMapper.INSTANCE.exerciseTemplateUpdateDtoToExerciseTemplate(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getBriefDescription()),
        () -> assertNotNull(this.dto.getExampleImages())
    );
  }

}
