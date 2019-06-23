package life.coachy.backend.profile.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import org.bson.types.ObjectId;

final class ProfileBuilder implements Buildable<Profile> {

  ObjectId userId;
  String website;
  String title;
  String bio;
  String bannerUrl;
  String location;
  Set<String> services;
  Set<String> socialLinks;

  private ProfileBuilder() {}

  public static ProfileBuilder create() {
    return new ProfileBuilder();
  }

  public ProfileBuilder withUserId(ObjectId userId) {
    this.userId = userId;
    return this;
  }

  public ProfileBuilder withWebsite(String website) {
    this.website = website;
    return this;
  }

  public ProfileBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  public ProfileBuilder withBio(String bio) {
    this.bio = bio;
    return this;
  }

  public ProfileBuilder withBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    return this;
  }

  public ProfileBuilder withServices(Set<String> services) {
    this.services = services;
    return this;
  }

  public ProfileBuilder withSocialLinks(Set<String> socialLinks) {
    this.socialLinks = socialLinks;
    return this;
  }

  public ProfileBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  @Override
  public Profile build() {
    return new Profile(this);
  }

}
