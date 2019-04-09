package life.coachy.backend.schedule.domain

import com.google.common.collect.Sets
import life.coachy.backend.exercise.dto.ExerciseDtoBuilder
import life.coachy.backend.schedule.day.ScheduleDayDto
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDtoBuilder
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateCommandDto
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateCommandDtoBuilder
import org.bson.types.ObjectId
import spock.lang.Specification

class ScheduleCreatorSpec extends Specification {

  def "'ScheduleCreateCommandDto' to 'Schedule' test"() {
    given: "schedule data transfer object"
      ScheduleCreateCommandDto dto = ScheduleCreateCommandDtoBuilder.create()
          .withName("test schedule")
          .withCreator(ObjectId.get())
          .withCharge(ObjectId.get())
          .withNote("brief note")
          .withActive(true)
          .withDays(Sets.newLinkedHashSet(Sets.newHashSet(
              new ScheduleDayDto("Monday", Sets.newLinkedHashSet(Sets.newHashSet(ExerciseDtoBuilder.create().withName("Flexing").build())), true)
          )))
          .build()
    when: "map schedule dto to schedule entity"
      Schedule schedule = new ScheduleCreator().from(dto);
    then:
      schedule != null
  }

  def "'ScheduleUpdateEntireEntityCommandDto' to 'Schedule' test"() {
    given: "schedule data transfer object"
      ScheduleUpdateCommandDto dto = ScheduleUpdateCommandDtoBuilder.create()
          .withName("test schedule")
          .withNote("brief note")
          .withActive(true)
          .withDays(Sets.newLinkedHashSet(Sets.newHashSet(
              new ScheduleDayDto("Monday", Sets.newLinkedHashSet(Sets.newHashSet(ExerciseDtoBuilder.create().withName("Flexing").build())), true)
          )))
          .build()
    when: "map schedule dto to schedule entity"
      Schedule schedule = new ScheduleCreator().from(dto);
    then:
      schedule != null
  }

}
