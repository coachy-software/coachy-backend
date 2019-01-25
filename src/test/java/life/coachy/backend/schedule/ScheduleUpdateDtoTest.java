package life.coachy.backend.schedule;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ScheduleUpdateDtoTest {

  @Test
  public void toEntityTest() {
    ScheduleUpdateDto dto = new ScheduleUpdateDto("testName", true, null);

    Assertions.assertEquals("Schedule{"
        + "identifier=null, "
        + "name='testName', "
        + "creator=null, "
        + "createdAt=null, "
        + "updatedAt=null, "
        + "active=true, "
        + "days=null}", dto.toEntity().toString());
  }

}
