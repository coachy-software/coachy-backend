package life.coachy.backend.message;

class OutputMessage extends Message {

  private String time;

  OutputMessage(final String from, final String text, final String time) {
    this.setFrom(from);
    this.setText(text);
    this.time = time;
  }

  public String getTime() {
    return this.time;
  }

}
