package life.coachy.backend.schedule.query;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
class ScheduleQueryDtoSerializer extends JsonSerializer<ScheduleQueryDto> {

  public void serialize(ScheduleQueryDto queryDto, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("identifier", queryDto.getIdentifier().toHexString());
    jsonGenerator.writeStringField("name", queryDto.getName());
    jsonGenerator.writeStringField("creator", queryDto.getCreator().toHexString());
    jsonGenerator.writeStringField("charge", queryDto.getCharge().toHexString());
    jsonGenerator.writeStringField("note", queryDto.getNote());
    jsonGenerator.writeStringField("createdAt", queryDto.getCreatedAt().toString());
    jsonGenerator.writeStringField("updatedAt", queryDto.getUpdatedAt().toString());
    jsonGenerator.writeBooleanField("active", queryDto.isActive());
    jsonGenerator.writeObjectField("days", queryDto.getDays());
    jsonGenerator.writeEndObject();
  }

}
