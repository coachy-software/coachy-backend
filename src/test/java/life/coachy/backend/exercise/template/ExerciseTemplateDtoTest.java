package life.coachy.backend.exercise.template;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateDto;
import life.coachy.backend.exercise.template.dto.ExerciseTemplateDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ExerciseTemplateDtoTest {

  private ObjectId id = ObjectId.get();
  private ExerciseTemplateDto dto = ExerciseTemplateDtoBuilder.createBuilder()
      .withName("testName123")
      .withBriefDescription("brief")
      .withExampleImages(Lists.newArrayList("example1", "example2"))
      .withIdentifier(this.id)
      .withVerified(true)
      .build();

  @Test
  public void toEntityTest() {
    assertEquals("ExerciseTemplate{"
            + "identifier=" + id + ", "
            + "name='testName123', "
            + "exampleImages=[example1, example2], "
            + "briefDescription='brief', "
            + "verified=true}",
        String.valueOf(ExerciseTemplateMapper.INSTANCE.exerciseTemplateDtoToExerciseTemplate(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getBriefDescription()),
        () -> assertNotNull(this.dto.getExampleImages()),
        () -> assertNotNull(this.dto.getIdentifier())
    );
  }

}
