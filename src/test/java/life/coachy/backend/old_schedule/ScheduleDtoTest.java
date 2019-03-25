package life.coachy.backend.old_schedule;

import java.time.LocalDateTime;
import life.coachy.backend.old_schedule.dto.ScheduleDto;
import life.coachy.backend.old_schedule.dto.ScheduleDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleDtoTest {

  private ObjectId id = ObjectId.get();
  private LocalDateTime createDate = LocalDateTime.now();
  private LocalDateTime updateDate = LocalDateTime.now();
  private ScheduleDto dto = ScheduleDtoBuilder.createBuilder()
      .withIdentifier(this.id)
      .withName("testName")
      .withCreatedAt(this.createDate)
      .withUpdatedAt(this.updateDate)
      .withActive(true)
      .build();

  @Test
  public void toEntityTest() {
    assertEquals("Schedule{"
        + "identifier=" + this.id + ", "
        + "name='testName', "
        + "creator=null, "
        + "charge=null, "
        + "createdAt=" + this.createDate + ", "
        + "updatedAt=" + this.updateDate + ", "
        + "active=true, "
        + "days=null}", String.valueOf(ScheduleMapper.INSTANCE.scheduleDtoToSchedule(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getIdentifier()),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getCreatedAt()),
        () -> assertNotNull(this.dto.getUpdatedAt()),
        () -> assertTrue(this.dto.isActive())
    );
  }

}
