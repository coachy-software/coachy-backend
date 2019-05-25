package life.coachy.backend.headway.measurement.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class MeasurementDto {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId id;
  private String name;
  private String value;
  private int reps;

  MeasurementDto() {}

  MeasurementDto(MeasurementDtoBuilder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.value = builder.value;
    this.reps = builder.reps;
  }

  public ObjectId getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getValue() {
    return this.value;
  }

  public int getReps() {
    return this.reps;
  }

}
