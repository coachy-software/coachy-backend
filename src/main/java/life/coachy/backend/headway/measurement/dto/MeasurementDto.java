package life.coachy.backend.headway.measurement.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class MeasurementDto {

  @JsonSerialize(using = ToStringSerializer.class) @Id private ObjectId id;
  private String name;
  private double value;

  MeasurementDto() {}

  MeasurementDto(MeasurementDtoBuilder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.value = builder.value;
  }

  public ObjectId getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public double getValue() {
    return this.value;
  }

}
