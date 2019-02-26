package life.coachy.backend.schedule;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
class ScheduleSerializer extends JsonSerializer<Schedule> {

  @Override
  public void serialize(Schedule schedule, JsonGenerator jsonGenerator, SerializerProvider provider)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("identifier", schedule.getIdentifier().toHexString());
    jsonGenerator.writeStringField("name", schedule.getName());
    jsonGenerator.writeStringField("creator", schedule.getCreator().getIdentifier().toHexString());
    jsonGenerator.writeStringField("charge", schedule.getCharge().getIdentifier().toHexString());
    jsonGenerator.writeStringField("note", schedule.getNote());
    jsonGenerator.writeStringField("createdAt", schedule.getCreatedAt().toString());
    jsonGenerator.writeStringField("updatedAt", schedule.getUpdatedAt().toString());
    jsonGenerator.writeBooleanField("active", schedule.isActive());
    jsonGenerator.writeObjectField("days", schedule.getDays());
    jsonGenerator.writeEndObject();
  }

}
