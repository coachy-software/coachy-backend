package life.coachy.backend.schedule;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import life.coachy.backend.schedule.day.ScheduleDayDto;
import life.coachy.backend.user.UserDto;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleBuilderTest {

  private ObjectId id = ObjectId.get();
  private Date createDate = Date.from(Instant.now());
  private Date updateDate = Date.from(Instant.now());
  private UserDto creator = new UserDto();
  private List<ScheduleDayDto> days = Collections.singletonList(new ScheduleDayDto());

  private Schedule schedule = new ScheduleBuilder()
      .withIdentifier(this.id)
      .withCreatedAt(this.createDate)
      .withUpdatedAt(this.updateDate)
      .withCreator(this.creator)
      .withDays(this.days)
      .withName("testName")
      .isActive(false)
      .build();

  @Test
  public void shouldNotBeNull() {
    assertNotNull(this.schedule);
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.schedule.getIdentifier()),
        () -> assertNotNull(this.schedule.getCreatedAt()),
        () -> assertNotNull(this.schedule.getUpdatedAt()),
        () -> assertNotNull(this.schedule.getCreator()),
        () -> assertNotNull(this.schedule.getDays()),
        () -> assertNotNull(this.schedule.getName())
    );
  }

  @Test
  public void toStringTest() {
    assertEquals("Schedule{"
        + "identifier=" + this.id + ", "
        + "name='testName', "
        + "creator=" + this.creator + ", "
        + "createdAt=" + this.createDate + ", "
        + "updatedAt=" + this.updateDate + ", "
        + "active=false, "
        + "days=" + this.days + "}", this.schedule.toString());
  }

}
