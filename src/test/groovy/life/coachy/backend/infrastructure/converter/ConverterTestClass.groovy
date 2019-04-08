package life.coachy.backend.infrastructure.converter

import life.coachy.backend.infrastructure.command.CommandDtoMarker

class ConverterTestClass implements CommandDtoMarker {

  String testField
  int testNumberField

  ConverterTestClass(String testField, int testNumberField) {
    this.testField = testField
    this.testNumberField = testNumberField
  }

  String getTestField() {
    return testField
  }

  int getTestNumberField() {
    return testNumberField
  }

}
