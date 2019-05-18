package life.coachy.backend.headway.domain

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.headway.SampleHeadways
import life.coachy.backend.headway.domain.exception.HeadwayNotFoundException
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class HeadwayFacadeSpec extends IntegrationSpec implements SampleHeadways {

  @Autowired HeadwayFacade headwayFacade

  def "method 'create' should store headway"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "user tries to create a headway"
      this.headwayFacade.create(sampleHeadwayCreateCommandDto)
    then: "the headway should be available to fetch"
      this.mongoTemplate.exists(Query.query(Criteria.where("ownerId").is(sampleHeadwayId)), Headway)
  }

  def "method 'delete' should delete a headway"() {
    given: "we have one headway and one user in system"
      setUpUser(sampleHeadwayId, "yang160", "password123", Collections.emptySet())
      setUpHeadway(sampleHeadwayId, sampleHeadwayId)
    when: "user tries to delete the headway"
      this.headwayFacade.delete(sampleHeadwayId)
    then: "the headway should be deleted"
      !this.mongoTemplate.exists(Query.query(Criteria.where("_id").is(sampleHeadwayId)), Headway)
  }

  def "method 'delete' should throw 'HeadwayNotFoundException' if does not exist"() {
    when: "user tries to delete the headway"
      this.headwayFacade.delete(ObjectId.get())
    then:
      thrown(HeadwayNotFoundException)
  }

  def "method 'fetchAllByOwnerId' should throw 'UserNotFoundException' if specified owner id does not belong to any existing user"() {
    when: "user tries to display it's headways"
      this.headwayFacade.fetchAllByOwnerId(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "method 'fetchOne' should throw 'HeadwayNotFoundException' if does not exist"() {
    when: "user tries to fetch a headway"
      this.headwayFacade.fetchOne(ObjectId.get())
    then:
      thrown(HeadwayNotFoundException)
  }

}
