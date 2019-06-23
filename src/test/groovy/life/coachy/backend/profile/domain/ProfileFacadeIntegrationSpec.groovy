package life.coachy.backend.profile.domain


import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.infrastructure.constant.MongoCollections
import life.coachy.backend.profile.SampleProfiles
import life.coachy.backend.user.domain.exception.UserNotFoundException
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class ProfileFacadeIntegrationSpec extends IntegrationSpec implements SampleProfiles {

  @Autowired ProfileFacade profileFacade;

  def "method 'createProfile' should store profile in system"() {
    when: "user creates a profile"
      this.profileFacade.createProfile(profileCreateDtoSample.getUserId())
    then:
      mongoTemplate.exists(Query.query(Criteria.where("userId").is(profileCreateDtoSample.getUserId())), MongoCollections.PROFILES)
  }

  def "method 'updateProfile' should update the profile"() {
    given: "we have one profile in system"
      def profile = setUpProfile(ObjectId.get(), ObjectId.get())
    when: "user tries to update the profile"
      this.profileFacade.updateProfile(profileUpdateDtoSample, profile.get("userId"))
    then:
      mongoTemplate.exists(Query.query(Criteria.where("userId").is(profile.get("userId")).and("website").is("updated website")), MongoCollections.PROFILES)
  }

  def "method 'updateProfile' should throw 'UserNotFoundException' if the profile does not belong to any user"() {
    when: "user tries to update the profile"
      this.profileFacade.updateProfile(profileUpdateDtoSample, ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "method 'fetchByUserId' should throw 'UserNotFoundException' if the profile does not belong to any user"() {
    when: "user tries to display the profile"
      this.profileFacade.fetchByUserId(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "method 'fetchByUserId' should display profile details"() {
    given: "we have one profile in system"
      def profile = setUpProfile(ObjectId.get(), ObjectId.get())
    when: "user tries to display the profile"
      def fetchResult = this.profileFacade.fetchByUserId(profile.get("userId"))
    then:
      fetchResult.getUserId() == profile.get("userId")
  }

  def "method 'toggleFollow(true)' should follow the profile"() {
    given: "we have two profiles in system"
      def followingProfile = setUpProfile(ObjectId.get(), ObjectId.get())
      def followerProfile = setUpProfile(ObjectId.get(), ObjectId.get())
    when: "user tries to follow the profile"
      this.profileFacade.toggleFollow(true, followingProfile.get("userId"), followerProfile.get("userId"))
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(followingProfile.get("_id")).and("followers").in(followerProfile.get("userId"))), MongoCollections.PROFILES)
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(followerProfile.get("_id")).and("following").in(followingProfile.get("userId"))), MongoCollections.PROFILES)
  }

  def "method 'toggleFollow(false)' should unfollow the profile"() {
    given: "we have two profiles in system"
      def followingProfile = setUpProfile(ObjectId.get(), ObjectId.get())
      def followerProfile = setUpProfile(ObjectId.get(), ObjectId.get())
    when: "user tries to follow the profile"
      this.profileFacade.toggleFollow(true, followingProfile.get("userId"), followerProfile.get("userId"))
    then:
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(followingProfile.get("_id")).and("followers").in(followerProfile.get("userId"))), MongoCollections.PROFILES)
      mongoTemplate.exists(Query.query(Criteria.where("_id").is(followerProfile.get("_id")).and("following").in(followingProfile.get("userId"))), MongoCollections.PROFILES)
    and:
      this.profileFacade.toggleFollow(false, followingProfile.get("userId"), followerProfile.get("userId"))
    then:
      !mongoTemplate.exists(Query.query(Criteria.where("_id").is(followingProfile.get("_id")).and("followers").in(followerProfile.get("userId"))), MongoCollections.PROFILES)
      !mongoTemplate.exists(Query.query(Criteria.where("_id").is(followerProfile.get("_id")).and("following").in(followingProfile.get("userId"))), MongoCollections.PROFILES)
  }

}
