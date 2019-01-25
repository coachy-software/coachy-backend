package life.coachy.backend.exercise.template;

import java.util.Arrays;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ExerciseTemplateBuilderTest {

  private ObjectId objectId = ObjectId.get();
  private ExerciseTemplate template = new ExerciseTemplateBuilder()
      .withName("test123")
      .withBriefDescription("brief description")
      .withExampleImages(Arrays.asList("test1", "test2"))
      .withIdentifier(this.objectId)
      .withVerified(true)
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
        + "identifier=" + this.objectId + ", "
        + "name='test123', "
        + "exampleImages=[test1, test2], "
        + "briefDescription='brief description', "
        + "verified=true}", this.template.toString());
  }

}
