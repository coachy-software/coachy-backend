package life.coachy.backend.user.domain

import com.google.common.collect.Sets
import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.user.SampleUsers
import life.coachy.backend.user.domain.exception.IncorrectCredentialsException
import life.coachy.backend.user.domain.exception.UserAlreadyExistsException
import life.coachy.backend.user.domain.exception.UserNotFoundException
import life.coachy.backend.user.query.UserQueryDto
import org.bson.types.ObjectId
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class UserFacadeIntegrationSpec extends IntegrationSpec implements SampleUsers {

  @Autowired UserFacade userFacade

  def "method 'register' should register an user"() {
    when: "user tries to register with username `yang160`"
      userFacade.register(sampleRegistrationUser)
    then: "system saves user to db"
      Query query = Query.query(Criteria.where("username").is("yang160"))
      mongoTemplate.exists(query, User)
  }

  def "method 'register' should throw 'UserAlreadyExistsException' when email or username are already in use"() {
    when: "two users try to register with username `yang160`"
      userFacade.register(sampleRegistrationUser)
      userFacade.register(sampleRegistrationUser)
    then:
      thrown(UserAlreadyExistsException)
  }

  def "method 'update' should update an user"() {
    given: "we have one user named 'yang160'"
      ObjectId id = ObjectId.get();
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "user tries to update username"
      userFacade.update(id, sampleUpdateUser)
    then:
      Query query = Query.query(Criteria.where("username").is("yang160_UPDATED"))
      mongoTemplate.exists(query, User)
  }

  def "method 'update' should throw 'UserAlreadyExistsException' when username is already in use"() {
    given: "we have one user named 'yang160'"
      ObjectId id = ObjectId.get();
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    and: "we have another user named 'yang160_UPDATED'"
      setUpUser(ObjectId.get(), "yang160_UPDATED", "password123", Collections.emptySet())
    when: "user tries to update username from 'yang160' to 'yang160_UPDATED'"
      userFacade.update(id, sampleUpdateUser)
    then:
      thrown(UserAlreadyExistsException)
  }

  def "method 'update' should throw 'UserNotFoundException' when user not found"() {
    when: "user tries to update username"
      userFacade.update(ObjectId.get(), sampleUpdateUser)
    then:
      thrown(UserNotFoundException)
  }

  def "method 'delete' should delete an user"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "user tries to delete an account"
      userFacade.delete(id)
    then:
      Query query = Query.query(Criteria.where("username").is("yang160"))
      !mongoTemplate.exists(query, User)
  }

  def "method 'delete' should throw 'UserNotFoundException' when user not found"() {
    when: "user tries to delete an account"
      userFacade.delete(ObjectId.get())
    then:
      thrown(UserNotFoundException)
  }

  def "method 'givePermissions' should give user specified permissions"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Collections.emptySet())
    when: "user tries to add some permissions"
      userFacade.givePermissions(id, "user.${id}.read")
    then:
      Query query = Query.query(Criteria.where("username").is("yang160").and("permissions").regex("user.${id}.read"))
      mongoTemplate.exists(query, User)
  }

  def "method 'nullifyPermissions' should take away specified permissions from user"() {
    given: "we have one user with some permissions in system"
      ObjectId id = ObjectId.get()
      setUpUser(id, "yang160", "password123", Sets.newHashSet("user.${id}.read", "user.${id}.update"))
    when: "user tries to remove permissions belong to #id"
      userFacade.nullifyPermissions(id, id)
    then:
      Query query = Query.query(Criteria.where("username").is("yang160").and("permissions").size(0))
      mongoTemplate.exists(query, User)
  }

  def "method 'resetPassword' should reset user's password"() {
    given: "we have one user in system"
      ObjectId id = ObjectId.get()
      BasicDBObject basicDBObject = setUpUser(id, "yang160", "password123", "yang160@gmail.com", Collections.emptySet())
    when: "user tries to reset it's password"
      userFacade.resetPassword("yang160@gmail.com", "newPassword123")
    then: "user must exists"
      Query userExistsQuery = Query.query(Criteria.where("username").is("yang160"));
      mongoTemplate.exists(userExistsQuery, User)
    and: "password must be different"
      Query passwordChangedQuery = Query.query(Criteria.where("username").is("yang160").and("password").is(basicDBObject.get("password")));
      !mongoTemplate.exists(passwordChangedQuery, User)
  }

  def "method 'resetPassword' should throw 'UserNotFoundException' when specified email does not belong to any account"() {
    when: "user tries to reset it's password"
      userFacade.resetPassword("yang160@gmail.com", "newPassword123")
    then:
      thrown(UserNotFoundException)
  }

  def "method 'changePassword' should throw 'IncorrectCredentialsException' if dto's old password doesnt match real old password"() {
    given: "we have one user in system"
      BasicDBObject user = setUpUser(ObjectId.get(), "yang160", "password1234", "yang160@gmail.com", Collections.emptySet())
    when: "user tries to change it's password"
      UserQueryDto userQueryDto = Mockito.mock(UserQueryDto)
      Mockito.when(userQueryDto.getPassword()).thenReturn(user.get("password"));
      Mockito.when(userQueryDto.getEmail()).thenReturn(user.get("email"));

      userFacade.changePassword(userQueryDto, sampleChangePasswordDto)
    then:
      thrown(IncorrectCredentialsException)
  }

}
