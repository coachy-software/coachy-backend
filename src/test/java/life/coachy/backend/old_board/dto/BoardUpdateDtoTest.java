package life.coachy.backend.old_board.dto;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import java.util.List;
import life.coachy.backend.old_board.BoardMapper;
import life.coachy.backend.old_board.label.dto.LabelDto;
import life.coachy.backend.old_board.task.dto.TaskDto;
import life.coachy.backend.old_board.task.dto.TaskDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoardUpdateDtoTest {

  private ObjectId id = ObjectId.get();
  private List<TaskDto> tasks = Lists.newArrayList(TaskDtoBuilder.createBuilder()
      .withName("testTask")
      .withColor("#2b2b2b")
      .withContent("test content")
      .withIdentifier(this.id)
      .build()
  );
  private List<LabelDto> labels = Lists.newArrayList(new LabelDto(this.id, "testName", this.tasks));

  private BoardUpdateDto dto = new BoardUpdateDto("testName", this.labels);

  @Test
  public void toEntityTest() {
    assertEquals("Board{"
        + "identifier=null, "
        + "name='testName', "
        + "labels=" + this.labels + ", "
        + "owner=null}", String.valueOf(BoardMapper.INSTANCE.boardUpdateDtoToBoard(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getLabels())
    );
  }

}
