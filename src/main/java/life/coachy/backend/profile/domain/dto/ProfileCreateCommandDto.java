package life.coachy.backend.profile.domain.dto;

import java.util.Set;
import org.bson.types.ObjectId;

public class ProfileCreateCommandDto {

  private ObjectId userId;
  private String website;
  private String title;
  private String bio;
  private String bannerUrl;
  private String location;
  private Set<String> services;
  private Set<String> socialLinks;

  ProfileCreateCommandDto() {}

  ProfileCreateCommandDto(ProfileCreateCommandDtoBuilder builder) {
    this.userId = builder.userId;
    this.website = builder.website;
    this.title = builder.title;
    this.bio = builder.bio;
    this.bannerUrl = builder.bannerUrl;
    this.location = builder.location;
    this.services = builder.services;
    this.socialLinks = builder.socialLinks;
  }

  public ObjectId getUserId() {
    return this.userId;
  }

  public String getWebsite() {
    return this.website;
  }

  public String getTitle() {
    return this.title;
  }

  public String getBio() {
    return this.bio;
  }

  public String getBannerUrl() {
    return this.bannerUrl;
  }

  public String getLocation() {
    return this.location;
  }

  public Set<String> getServices() {
    return this.services;
  }

  public Set<String> getSocialLinks() {
    return this.socialLinks;
  }

}
