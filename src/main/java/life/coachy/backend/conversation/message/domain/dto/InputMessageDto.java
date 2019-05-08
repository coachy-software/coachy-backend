package life.coachy.backend.conversation.message.domain.dto;

import java.time.LocalDateTime;

public class InputMessageDto {

  private String from;
  private String to;
  private String body;
  private LocalDateTime date;

  public String getFrom() {
    return this.from;
  }

  public String getTo() {
    return this.to;
  }

  public String getBody() {
    return this.body;
  }

}
