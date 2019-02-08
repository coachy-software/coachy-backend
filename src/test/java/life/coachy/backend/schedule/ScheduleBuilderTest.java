package life.coachy.backend.schedule;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.schedule.day.dto.ScheduleDayDtoBuilder;
import life.coachy.backend.user.dto.UserDto;
import life.coachy.backend.user.dto.UserDtoBuilder;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleBuilderTest {

  private ObjectId id = ObjectId.get();
  private LocalDateTime createDate = LocalDateTime.now();
  private LocalDateTime updateDate = LocalDateTime.now();
  private UserDto creator = UserDtoBuilder.createBuilder().withIdentifier(this.id).build();
  private List<ScheduleDayDto> days = Collections.singletonList(ScheduleDayDtoBuilder.createBuilder().build());

  private Schedule schedule = ScheduleBuilder.createBuilder()
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
        + "charge=null, "
        + "createdAt=" + this.createDate + ", "
        + "updatedAt=" + this.updateDate + ", "
        + "active=false, "
        + "days=" + this.days + "}", this.schedule.toString());
  }

}
