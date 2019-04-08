package life.coachy.backend.infrastructure.util

import spock.lang.Specification

class FilenameUtilSpec extends Specification {

  def "'getExtension' method test"() {
    given:
      String fileName = "inappropriateImage.png"
    expect:
      FilenameUtil.getExtension(fileName) == "png"
  }

  def "'getExtension' method should throw NullPointerException if first argument is null"() {
    when:
      FilenameUtil.getExtension(null)
    then:
      thrown(NullPointerException)
  }

  def "'getExtension' should return empty string if provided file name has no extension"() {
    given:
      String fileName = "inappropriateImage"
    expect:
      FilenameUtil.getExtension(fileName) == ""
  }

  def "'getExtension' should return empty string if provided file name has separator as the last char"() {
    given:
      String fileName = "inappropriateImage."
    expect:
      FilenameUtil.getExtension(fileName) == ""
  }

}
