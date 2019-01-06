package life.coachy.backend.util.crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import life.coachy.backend.util.AbstractDto;

public class TestDto extends AbstractDto<TestEntity> {

  @NotEmpty
  @JsonProperty
  private String name;
  @NotEmpty
  private String something;

  public TestDto(String name, String something) {
    this.name = name;
    this.something = something;
  }

  public TestDto() {
  }

  @Override
  public String getName() {
    return this.name;
  }

  public String getSomething() {
    return this.something;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSomething(String something) {
    this.something = something;
  }

  public TestEntity toEntity() {
    return new TestEntity(this.name, this.something);
  }

}
