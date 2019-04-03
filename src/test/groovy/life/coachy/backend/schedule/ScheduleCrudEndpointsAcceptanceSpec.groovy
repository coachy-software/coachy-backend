package life.coachy.backend.schedule

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ScheduleCrudEndpointsAcceptanceSpec extends IntegrationSpec implements SampleSchedules {

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
