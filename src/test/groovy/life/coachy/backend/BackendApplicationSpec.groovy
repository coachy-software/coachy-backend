package life.coachy.backend

import life.coachy.backend.base.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext

class BackendApplicationSpec extends IntegrationSpec {

  @Autowired WebApplicationContext webApplicationContext;

  def "context test"() {
    webApplicationContext != null
  }

}
