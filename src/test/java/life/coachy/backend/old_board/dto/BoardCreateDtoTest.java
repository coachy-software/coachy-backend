package life.coachy.backend.old_board.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.common.collect.Lists;
import java.util.List;
import life.coachy.backend.old_board.label.dto.LabelDto;
import life.coachy.backend.old_board.task.dto.TaskDto;
import life.coachy.backend.old_board.task.dto.TaskDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoardCreateDtoTest {

  private ObjectId id = ObjectId.get();
  private List<TaskDto> tasks = Lists.newArrayList(TaskDtoBuilder.createBuilder()
      .withName("testTask")
      .withColor("#2b2b2b")
      .withContent("test content")
      .withIdentifier(this.id)
      .build()
  );
  private List<LabelDto> labels = Lists.newArrayList(new LabelDto(this.id, "testName", this.tasks));
  private BoardCreateDto dto = new BoardCreateDto("testName", this.labels, null);

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getLabels())
    );
  }

}
