package life.coachy.backend.schedule.day;

import life.coachy.backend.schedule.day.dto.ScheduleDayDto;
import life.coachy.backend.schedule.day.dto.ScheduleDayDtoBuilder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class ScheduleDayDtoTest {

  private ScheduleDayDto dto = ScheduleDayDtoBuilder.createBuilder()
      .withName("testName")
      .withExercises(null) // todo
      .withMusclesPart("arms")
      .withTrainingDay(true)
      .build();

  @Test
  public void toEntityTest() {
    Assertions.assertEquals("ScheduleDay{"
        + "name='testName', "
        + "musclesPart='arms', "
        + "exercises=null, "
        + "trainingDay=true}", String.valueOf(ScheduleDayMapper.INSTANCE.scheduleDayDtoToScheduleDay(this.dto)));
  }

  @Test
  public void valuesShouldNotBeNull() {
    assertAll(
        () -> assertNotNull(this.dto),
        () -> assertNotNull(this.dto.getName()),
        () -> assertNotNull(this.dto.getMusclesPart()),
        () -> assertTrue(this.dto.isTrainingDay())
    );
  }

}
