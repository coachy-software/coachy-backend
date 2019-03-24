package life.coachy.backend.old_exercise.template;

import java.util.Arrays;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ExerciseTemplateBuilderTest {

  private ObjectId objectId = ObjectId.get();
  private ExerciseTemplate template = ExerciseTemplateBuilder.createBuilder()
      .withName("test123")
      .withBriefDescription("brief description")
      .withExampleImages(Arrays.asList("test1", "test2"))
      .withIdentifier(this.objectId)
      .build();

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.template),
        () -> assertNotNull(this.template.getName()),
        () -> assertNotNull(this.template.getBriefDescription()),
        () -> assertNotNull(this.template.getExampleImages()),
        () -> assertNotNull(this.template.getIdentifier())
    );
  }

  @Test
  public void toStringTest() {
    assertEquals("ExerciseTemplate{"
        + "identifier=" + objectId + ", "
        + "name='test123', "
        + "exampleImages=[test1, test2], "
        + "briefDescription='brief description', "
        + "muscleGroup='null'}", this.template.toString());
  }

}
