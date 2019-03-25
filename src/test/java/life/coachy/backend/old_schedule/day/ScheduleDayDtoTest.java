package life.coachy.backend.old_schedule.day;

import life.coachy.backend.old_schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.old_schedule.day.dto.ScheduleDayDtoBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleDayDtoTest {

  private ScheduleDayDto dto = ScheduleDayDtoBuilder.createBuilder()
      .withName("testName")
      .withExercises(null)
      .withTrainingDay(true)
      .build();

  @Test
  public void toEntityTest() {
    Assertions.assertEquals("ScheduleDay{"
        + "name='testName', "
        + "exercises=null, "
        + "trainingDay=true}", String.valueOf(ScheduleDayMapper.INSTANCE.scheduleDayDtoToScheduleDay(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertTrue(this.dto.isTrainingDay())
    );
  }

}
