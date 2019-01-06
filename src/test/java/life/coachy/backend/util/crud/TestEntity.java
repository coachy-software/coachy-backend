package life.coachy.backend.util.crud;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import life.coachy.backend.util.IdentifiableEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tests")
public class TestEntity implements IdentifiableEntity<ObjectId> {

  @Id
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId identifier;
  private String name;
  private String something;

  public TestEntity(ObjectId identifier, String name, String something) {
    this.identifier = identifier;
    this.name = name;
    this.something = something;
  }

  public TestEntity(String name, String something) {
    this.name = name;
    this.something = something;
  }

  public TestEntity() {
  }

  @Override
  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public String getName() {
    return this.name;
  }

  public String getSomething() {
    return this.something;
  }

  public void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSomething(String something) {
    this.something = something;
  }

  @Override
  public String toString() {
    return "TestEntity{" +
        "identifier=" + identifier +
        ", name='" + name + '\'' +
        ", something='" + something + '\'' +
        '}';
  }

}
