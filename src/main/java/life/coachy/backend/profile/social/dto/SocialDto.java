package life.coachy.backend.profile.social.dto;

public class SocialDto {

  private String name;
  private String link;
  private String icon;

  SocialDto() {}

  public SocialDto(String name, String link, String icon) {
    this.name = name;
    this.link = link;
    this.icon = icon;
  }

  public String getName() {
    return this.name;
  }

  public String getLink() {
    return this.link;
  }

  public String getIcon() {
    return this.icon;
  }

}
