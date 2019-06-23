package life.coachy.backend.profile.domain.dto;

import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;

public final class ProfileUpdateCommandDtoBuilder implements Buildable<ProfileUpdateCommandDto> {

  String website;
  String title;
  String bio;
  String bannerUrl;
  String location;
  Set<String> services;
  Set<String> socialLinks;

  private ProfileUpdateCommandDtoBuilder() {}

  public static ProfileUpdateCommandDtoBuilder create() {
    return new ProfileUpdateCommandDtoBuilder();
  }

  public ProfileUpdateCommandDtoBuilder withWebsite(String website) {
    this.website = website;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withBio(String bio) {
    this.bio = bio;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withServices(Set<String> services) {
    this.services = services;
    return this;
  }

  public ProfileUpdateCommandDtoBuilder withSocialLinks(Set<String> socialLinks) {
    this.socialLinks = socialLinks;
    return this;
  }

  public ProfileUpdateCommandDto build() {
    return new ProfileUpdateCommandDto(this);
  }

}
