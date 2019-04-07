package life.coachy.backend.exercise.template.domain

import com.mongodb.BasicDBObject
import life.coachy.backend.base.IntegrationSpec
import life.coachy.backend.exercise.template.domain.exception.ExerciseTemplateNotFoundException
import life.coachy.backend.exercise.template.query.ExerciseTemplateQueryDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

class ExerciseTemplateFacadeIntegrationSpec extends IntegrationSpec {

  @Autowired ExerciseTemplateFacade facade

  def "method 'fetchOne' should return a template"() {
    given: "we have one exercise template in system"
      BasicDBObject exercise = setUpExerciseTemplate(ObjectId.get(), "test exercise template")
    when: "I go to /api/exercises/{id}"
      ExerciseTemplateQueryDto queryDto = facade.fetchOne(exercise.get("_id"))
    then: "I see exercise template query dto"
      queryDto.name == "test exercise template"
      queryDto.identifier == exercise.get("_id")
  }

  def "method 'fetchOne' should throw 'ExerciseTemplateNotFoundException' when id does not match any template"() {
    when: "I go to /api/exercises/{id}"
      facade.fetchOne(ObjectId.get())
    then:
      thrown(ExerciseTemplateNotFoundException)
  }

}
