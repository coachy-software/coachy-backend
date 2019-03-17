package life.coachy.backend.board.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import java.util.List;
import life.coachy.backend.board.BoardMapper;
import life.coachy.backend.board.label.dto.LabelDto;
import life.coachy.backend.board.task.dto.TaskDto;
import life.coachy.backend.board.task.dto.TaskDtoBuilder;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoardDtoTest {

  private ObjectId id = ObjectId.get();
  private List<TaskDto> tasks = Lists.newArrayList(TaskDtoBuilder.createBuilder()
      .withName("testTask")
      .withColor("#2b2b2b")
      .withContent("test content")
      .withIdentifier(this.id)
      .build()
  );
  private List<LabelDto> labels = Lists.newArrayList(new LabelDto(this.id, "testName", this.tasks));

  private BoardDto dto = BoardDtoBuilder.createBuilder()
      .withIdentifier(this.id)
      .withName("testName")
      .withLabel(this.labels)
      .build();

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getIdentifier()),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getLabels())
    );
  }

}
