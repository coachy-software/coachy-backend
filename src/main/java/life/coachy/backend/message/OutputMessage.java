package life.coachy.backend.message;

class OutputMessage extends Message {

  private String time;

  OutputMessage(String from, String text, String time) {
    this.setFrom(from);
    this.setText(text);
    this.time = time;
  }

  public String getTime() {
    return this.time;
  }

}
