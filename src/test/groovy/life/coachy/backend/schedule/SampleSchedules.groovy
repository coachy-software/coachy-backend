package life.coachy.backend.schedule

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import groovy.transform.CompileStatic
import life.coachy.backend.exercise.dto.ExerciseDtoBuilder
import life.coachy.backend.exercise.template.domain.dto.ExerciseTemplateCommandDtoBuilder
import life.coachy.backend.schedule.day.ScheduleDayDto
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDto
import life.coachy.backend.schedule.domain.dto.ScheduleCreateCommandDtoBuilder
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDto
import life.coachy.backend.schedule.domain.dto.ScheduleUpdateEntireEntityCommandDtoBuilder
import org.bson.types.ObjectId

@CompileStatic
trait SampleSchedules {

  ObjectId id = ObjectId.get();

  ScheduleCreateCommandDto sampleCreateDto = ScheduleCreateCommandDtoBuilder.create()
      .withName("test schedule")
      .withCreator(id)
      .withCharge(id)
      .withNote("brief note")
      .withActive(true)
      .withDays(Sets.newLinkedHashSet(Sets.newHashSet(
          new ScheduleDayDto("Monday", Sets.newLinkedHashSet(Sets.newHashSet(ExerciseDtoBuilder.create()
              .withName("Flexing")
              .withIdentifier(ObjectId.get())
              .withMiniSets(0)
              .withSets(3)
              .withReps(10)
              .withTemplate(ExerciseTemplateCommandDtoBuilder.create()
                  .withIdentifier(ObjectId.get())
                  .withName("test name")
                  .withBriefDescription("test desc")
                  .withExampleImages(Lists.newArrayList("test", "test2"))
                  .withMuscleGroup("Biceps")
                  .build())
              .build()
          )), true)
      )))
      .build()

  ScheduleUpdateEntireEntityCommandDto sampleUpdateDto = ScheduleUpdateEntireEntityCommandDtoBuilder.create()
      .withName("test schedule updated")
      .withNote("brief note")
      .withActive(true)
      .withDays(Sets.newLinkedHashSet(Sets.newHashSet(
          new ScheduleDayDto("Monday", Sets.newLinkedHashSet(Sets.newHashSet(ExerciseDtoBuilder.create().withName("Flexing").build())), true)
      )))
      .build()

}
