package life.coachy.backend.user.domain

import life.coachy.backend.base.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class UserFacadeSpec extends IntegrationSpec implements SampleUsers {

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  UserFacade userFacade;

  def "method 'register' should register an user"() {
    when: "user is trying to register with username `yang160`"
      userFacade.register(sampleRegistrationUser);
    then: "user saved to db"
      Query query = Query.query(Criteria.where("username").is("yang160"));
      this.mongoTemplate.exists(query, User.class);
  }

  def "length of Spock's and his friends' names"() {
    expect:
      name.size() == length

    where:
      name     | length
      "Spock"  | 5
      "Kirk"   | 4
      "Scotty" | 6
  }

}
