package life.coachy.backend.old_schedule;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import life.coachy.backend.old_schedule.dto.ScheduleUpdateDto;
import life.coachy.backend.old_schedule.dto.ScheduleUpdateDtoBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ScheduleUpdateDtoTest {

  private ScheduleUpdateDto dto = ScheduleUpdateDtoBuilder.createBuilder()
      .withName("testName")
      .withActive(true)
      .build();

  @Test
  public void toEntityTest() {
    Assertions.assertEquals("Schedule{"
        + "identifier=null, "
        + "name='testName', "
        + "creator=null, "
        + "charge=null, "
        + "createdAt=null, "
        + "updatedAt=null, "
        + "active=true, "
        + "days=null}", String.valueOf(ScheduleMapper.INSTANCE.scheduleUpdateDtoToSchedule(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertTrue(this.dto.isActive())
    );
  }
}
