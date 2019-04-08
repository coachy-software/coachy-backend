package life.coachy.backend.infrastructure.converter

import spock.lang.Specification

class PropertiesToMapConverterSpec extends Specification {

  def "convert test"() {
    when:
      Map<String, Object> properties = new PropertiesToMapConverter().convert(new ConverterTestClass("test field content", 2115))
    then:
      properties.get("testField") == "test field content"
      properties.get("testNumberField") == 2115
  }

}
