package life.coachy.backend.infrastructure.converter

import org.json.JSONObject
import spock.lang.Specification

class ObjectToJsonConverterSpec extends Specification {

  def "convert test"() {
    when:
      String jsonContent = new ObjectToJsonConverter().convert(new ConverterTestClass("test field content", 2115))
    then:
      jsonContent == '{"testField":"test field content","testNumberField":2115}'
      new JSONObject(jsonContent).get("testField").toString() == "test field content"
      Integer.valueOf(new JSONObject(jsonContent).get("testNumberField")) == 2115
  }

}
