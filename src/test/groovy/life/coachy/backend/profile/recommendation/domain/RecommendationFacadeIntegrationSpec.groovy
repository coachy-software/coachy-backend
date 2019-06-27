package life.coachy.backend.profile.recommendation.domain

import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.base.UncompilableByCI
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.profile.recommendation.SampleRecommendations
import life.coachy.backend.profile.recommendation.domain.exception.RecommendationNotFoundException
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class RecommendationFacadeIntegrationSpec extends IntegrationSpec implements SampleRecommendations {

  @Autowired RecommendationFacade recommendationFacade;

  @UncompilableByCI
  def "'create' method should store recommendation in system"() {
    given: "we have two users and one profile in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "user tries to create a recommendation"
      def uri = recommendationFacade.create(recommendationCreateSample)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("from").is(recommendationCreateSample.getFrom())), MongoCollections.RECOMMENDATIONS)
  }

  def "'create' method should throw 'UserNotFoundException' if profile's user does not exist"() {
    given: "we have one user and one profile in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "user tries to create a recommendation"
      recommendationFacade.create(recommendationCreateSample)
    then:
      thrown(UserNotFoundException)
  }

  def "'create' method should throw 'UserNotFoundException' if recommendation's author does not exist"() {
    given: "we have one users and one profile in system"
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang160", "password123", Collections.emptySet())
      setUpProfile(ObjectId.get(), recommendationCreateSample.getProfileUserId())
    when: "user tries to create a recommendation"
      recommendationFacade.create(recommendationCreateSample)
    then:
      thrown(UserNotFoundException)
  }

  @UncompilableByCI
  def "'requestRevision' method should give update permission to recommendation's onwer"() {
    given: "we have two users and one recommendation in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
    when: "user requests a revision"
      recommendationFacade.requestRevision(recommendation.get("_id"))
    then:
      def permissionName = "recommendation.${recommendation.get("_id")}.update".toString();
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(recommendationCreateSample.getFrom()).and("permissions").in(permissionName)), MongoCollections.USERS)
  }

  def "'requestRevision' method should throw 'UserNotFoundException' if profile's user does not exist"() {
    given: "we have one user and one recommendation in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
    when: "user requests a revision"
      recommendationFacade.requestRevision(recommendation.get("_id"))
    then:
      thrown(UserNotFoundException)
  }

  def "'requestRevision' method should throw 'UserNotFoundException' if recommendation's author does not exist"() {
    given: "we have one users and one recommendation in system"
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
    when: "user requests a revision"
      recommendationFacade.requestRevision(recommendation.get("_id"))
    then:
      thrown(UserNotFoundException)
  }

  def "'requestRevision' method should throw 'RecommendationNotFoundException' if specified id does not belong to any entity"() {
    when: "user requests a revision"
      recommendationFacade.requestRevision(ObjectId.get())
    then:
      thrown(RecommendationNotFoundException)
  }

  def "'commitRevision' method should remove the permission from recommendation's creator"() {
    given: "we have two users and one recommendation in system"
      setUpUser(recommendationCreateSample.getFrom(), "yang160", "password123", Collections.emptySet())
      setUpUser(recommendationCreateSample.getProfileUserId(), "yang161", "password123", Collections.emptySet())
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
    when: "user tries to update the recommendation"
      recommendationFacade.commitRevision(recommendation.get("_id"), recommendationUpdateSample)
    then:
      def permissionName = "recommendation.${recommendation.get("_id")}.update".toString();
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(recommendation.get("_id")).and("content").is("updated test content")), MongoCollections.RECOMMENDATIONS)
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(recommendationCreateSample.getFrom()).and("permissions").nin(permissionName)), MongoCollections.USERS)
  }

  def "'commitRevision' method should throw 'RecommendationNotFoundException' if specified id does not belong to any entity"() {
    when: "user tries to update the recommendation"
      recommendationFacade.commitRevision(ObjectId.get(), recommendationUpdateSample)
    then:
      thrown(RecommendationNotFoundException)
  }

  def "'changeVisibilityStatus' method should change visibility status of the recommendation"() {
    given: "we have one recommendation in system"
      def recommendation = setUpRecommendation(ObjectId.get(), recommendationCreateSample.getProfileUserId(), recommendationCreateSample.getFrom())
    when: "user tries to change the recommendation's status"
      recommendationFacade.changeVisibilityStatus(recommendation.get("_id"), true)
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(recommendation.get("_id")).and("visible").is(true)), MongoCollections.RECOMMENDATIONS)
  }

  def "'changeVisibilityStatus' method should throw 'RecommendationNotFoundException' if specified id does not belong to any entity"() {
    when: "user tries to change the recommendation's status"
      recommendationFacade.changeVisibilityStatus(ObjectId.get(), true)
    then:
      thrown(RecommendationNotFoundException);
  }

}
