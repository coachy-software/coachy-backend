package life.coachy.backend.schedule.domain

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.schedule.SampleSchedules
import life.coachy.backend.schedule.domain.exception.ScheduleNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ScheduleFacadeIntegrationSpec extends IntegrationSpec implements SampleSchedules {

  @Autowired ScheduleFacade scheduleFacade;

  @Profile("!ci")
  def "method 'create' should create a schedule"() {
    given: "we have one user in system"
      setUpUser(sampleCreateDto.getCreator(), "yang160", "password123", Collections.emptySet())
    when: "user tries to create the schedule"
      scheduleFacade.create(sampleCreateDto)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("creator").is(id)), Schedule)
  }

  def "method 'update' should update a schedule"() {
    given: "we have one user and one schedule in system"
      ObjectId scheduleId = ObjectId.get();
      BasicDBObject schedule = setUpSchedule(scheduleId, "test name", id, id)
      setUpUser(id, "yang160", "password123", Sets.newHashSet("schedule.${schedule.get("_id")}.update"))
    when: "user tries to update the schedule"
      scheduleFacade.update(scheduleId, sampleUpdateDto)
    then: "schedule's name has been changed"
      mongoTemplate.exists(Query.query(Criteria.where("name").is(sampleUpdateDto.name)), Schedule)
  }

  def "method 'update' should throw 'ScheduleNotFoundException' when schedule not found"() {
    when: "user tries to update the schedule"
      scheduleFacade.update(ObjectId.get(), sampleUpdateDto)
    then:
      thrown(ScheduleNotFoundException)
  }

  def "method 'delete' should delete a schedule"() {
    given: "we have one schedule in system"
      setUpUser(id, "yang160", "password123", Collections.emptySet())
      BasicDBObject schedule = setUpSchedule(ObjectId.get(), "test name", id, id)
    when: "user tries to delete the schedule"
      scheduleFacade.delete(schedule.get("_id"))
    then:
      !mongoTemplate.exists(Query.query(Criteria.where("creator").is(id)), Schedule)
  }

  def "method 'delete' should throw 'ScheduleNotFoundException' when schedule not found"() {
    when: "user tries to delete the schedule"
      scheduleFacade.delete(ObjectId.get())
    then:
      thrown(ScheduleNotFoundException)
  }

}

