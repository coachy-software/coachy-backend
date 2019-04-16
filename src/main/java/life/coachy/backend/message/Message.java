package life.coachy.backend.message;

class Message {

  private String from;
  private String to;
  private String text;

  public String getFrom() {
    return this.from;
  }

  public String getTo() {
    return this.to;
  }

  public String getText() {
    return this.text;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public void setText(String text) {
    this.text = text;
  }

}
