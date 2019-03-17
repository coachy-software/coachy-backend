package life.coachy.backend.schedule;

import java.time.LocalDateTime;
import life.coachy.backend.schedule.dto.ScheduleGlobalDto;
import life.coachy.backend.schedule.dto.ScheduleGlobalDtoBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleGlobalDtoTest {

  private LocalDateTime createDate = LocalDateTime.now();
  private LocalDateTime updateDate = LocalDateTime.now();
  private ScheduleGlobalDto dto = ScheduleGlobalDtoBuilder.createBuilder()
      .withName("testName")
      .withCreatedAt(this.createDate)
      .withUpdatedAt(this.updateDate)
      .withActive(true)
      .build();
  private Schedule schedule = ScheduleMapper.INSTANCE.scheduleGlobalDtoToSchedule(this.dto);

  @Test
  public void toEntityTest() {
    assertEquals("Schedule{"
        + "identifier=null, "
        + "name='testName', "
        + "creator=" + this.schedule.getCreator() + ", "
        + "charge=" + this.schedule.getCharge() + ", "
        + "createdAt=" + this.createDate + ", "
        + "updatedAt=" + this.updateDate + ", "
        + "active=true, "
        + "days=null}", String.valueOf(this.schedule));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getCreatedAt()),
        () -> assertNotNull(this.dto.getUpdatedAt()),
        () -> assertTrue(this.dto.isActive())
    );
  }

}
