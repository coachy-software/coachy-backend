package life.coachy.backend.schedule

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.test.annotation.IfProfileValue
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.is
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ScheduleCrudEndpointAcceptanceSpec extends IntegrationSpec implements SampleSchedules {

  @Autowired ObjectToJsonConverter objectToJsonConverter

  def "positive schedule update scenario"() {
    given: "we have one schedule and one user in system"
      ObjectId scheduleId = ObjectId.get();
      setUpSchedule(scheduleId, "test schedule", id, id)
      setUpUser(id, "yang160", "password123", Sets.newHashSet("schedule.${scheduleId}.read", "schedule.${scheduleId}.update", "schedule.${scheduleId}.delete"))
    when: "I put to /api/schedules/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/schedules/{id}", scheduleId)
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "schedule has been updated"
      updateEndpoint.andExpect(status().isNoContent())
    when: "I go to /api/schedules/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get("/api/schedules/{id}", scheduleId)
          .with(httpBasic("yang160", "password123")))
    then: "I see that name changed"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.name', is("test schedule updated")))
    when: "I delete to /api/schedules/{id}"
      ResultActions deleteEndpoint = mockMvc.perform(delete("/api/schedules/{id}", scheduleId)
          .with(httpBasic("yang160", "password123")))
    then: "schedule has been deleted"
      deleteEndpoint.andExpect(status().isNoContent())
  }

  def "negative schedule update scenario"() {
    when: "I put to /api/schedules/{id}"
      ResultActions updateEndpoint = mockMvc.perform(put("/api/schedules/{id}", ObjectId.get())
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleUpdateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then:
      updateEndpoint.andExpect(status().isUnauthorized())
  }

  @IfProfileValue(name = "spring.profiles.active", value = "nonCI")
  def "positive create scenario"() {
    given: "we have one user in system"
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "I post to /api/schedules"
      ResultActions createEndpoint = mockMvc.perform(post("/api/schedules")
          .with(httpBasic("yang160", "password123"))
          .content(objectToJsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "user has been created with Location header"
      createEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I go to /api/schedules/{id}"
      ResultActions detailsEndpoint = mockMvc.perform(get(createEndpoint.andReturn().getResponse().getRedirectedUrl())
          .with(httpBasic("yang160", "password123")))
    then: "I see schedule details"
      detailsEndpoint.andExpect(status().isOk())
          .andExpect(jsonPath('$.note', is("brief note")))

  }

  def "negative create scenario"() {
    when: "I post to /api/schedules"
      ResultActions createEndpoint = mockMvc.perform(post("/api/schedules")
          .content(objectToJsonConverter.convert(sampleCreateDto))
          .contentType(MediaType.APPLICATION_JSON))
    then: "user has been created with Location header"
      createEndpoint.andExpect(status().isUnauthorized())
  }

  def "should display schedules"() {
    given: "we have two schedules and one user in system"
      BasicDBObject schedule = setUpSchedule(ObjectId.get(), "test schedule", id, id)
      BasicDBObject secondSchedule = setUpSchedule(ObjectId.get(), "123123123", id, id)
      setUpUser(id, "yang160", "password123", Sets.newHashSet("schedule.${schedule.get("_id")}.read"))
    when: "I go to /api/schedules"
      ResultActions schedulesEndpoint = mockMvc.perform(get("/api/schedules")
      .with(httpBasic("yang160", "password123")))
    then:
      schedulesEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${schedule.get("_id")}", "name": "${schedule.get("name")}", "charge": "${schedule.get("charge")}", "creator": "${schedule.get("creator")}"},
              {"identifier": "${secondSchedule.get("_id")}", "name": "${secondSchedule.get("name")}", "charge": "${schedule.get("charge")}", "creator": "${secondSchedule.get("creator")}"}
            ]
          """))
    when: "I go to /api/schedules/{id}"
      ResultActions scheduleEndpoint = mockMvc.perform(get("/api/schedules/{id}", schedule.get("_id"))
          .with(httpBasic("yang160", "password123")))
    then:
      scheduleEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            {"identifier": "${schedule.get("_id")}", "name": "${schedule.get("name")}", "charge": "${schedule.get("charge")}", "creator": "${schedule.get("creator")}"}
          """))
    when: "I go to /api/schedules?page=0&size=1"
      ResultActions paginationEndpoint = mockMvc.perform(get("/api/schedules?page=0&size=1")
          .with(httpBasic("yang160", "password123")))
    then: "I see only one schedule"
      paginationEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${schedule.get("_id")}", "name": "${schedule.get("name")}", "charge": "${schedule.get("charge")}", "creator": "${schedule.get("creator")}"}
            ]
          """))
    when: "I go to /api/schedules?name=test%20schedule"
      ResultActions searchEndpoint = mockMvc.perform(get(new URI("/api/schedules?name=test%20schedule"))
          .with(httpBasic("yang160", "password123"))
          .contentType(MediaType.APPLICATION_JSON))
    then: "I see only one schedule named 'test schedule'"
      searchEndpoint.andExpect(status().isOk())
          .andExpect(content().json("""
            [
              {"identifier": "${schedule.get("_id")}", "name": "${schedule.get("name")}", "charge": "${schedule.get("charge")}", "creator": "${schedule.get("creator")}"}
            ]
          """))
    }

}
