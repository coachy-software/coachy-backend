package life.coachy.backend.profile.domain.dto;

import java.util.LinkedHashSet;
import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.profile.social.dto.SocialDto;
import org.bson.types.ObjectId;

public final class ProfileCreateCommandDtoBuilder implements Buildable<ProfileCreateCommandDto> {

  ObjectId userId;
  String website;
  String title;
  String bio;
  String bannerUrl;
  String location;
  LinkedHashSet<String> services;
  LinkedHashSet<SocialDto> socialLinks;

  private ProfileCreateCommandDtoBuilder() {}

  public static ProfileCreateCommandDtoBuilder create() {
    return new ProfileCreateCommandDtoBuilder();
  }

  public ProfileCreateCommandDtoBuilder withUserId(ObjectId userId) {
    this.userId = userId;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withWebsite(String website) {
    this.website = website;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withBio(String bio) {
    this.bio = bio;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withServices(LinkedHashSet<String> services) {
    this.services = services;
    return this;
  }

  public ProfileCreateCommandDtoBuilder withSocialLinks(LinkedHashSet<SocialDto> socialLinks) {
    this.socialLinks = socialLinks;
    return this;
  }

  @Override
  public ProfileCreateCommandDto build() {
    return new ProfileCreateCommandDto(this);
  }

}
