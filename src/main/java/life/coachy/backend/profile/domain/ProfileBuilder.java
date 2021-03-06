package life.coachy.backend.profile.domain;

import java.util.LinkedHashSet;
import java.util.Set;
import life.coachy.backend.infrastructure.util.Buildable;
import life.coachy.backend.profile.social.dto.SocialDto;
import org.bson.types.ObjectId;

final class ProfileBuilder implements Buildable<Profile> {

  ObjectId identifier;
  ObjectId userId;
  String website;
  String title;
  String bio;
  String bannerUrl;
  String location;
  LinkedHashSet<String> services;
  LinkedHashSet<SocialDto> socialLinks;
  Set<ObjectId> followers;
  Set<ObjectId> following;

  private ProfileBuilder() {}

  public static ProfileBuilder create() {
    return new ProfileBuilder();
  }

  ProfileBuilder withIdentifier(ObjectId identifier) {
    this.identifier = identifier;
    return this;
  }

  ProfileBuilder withUserId(ObjectId userId) {
    this.userId = userId;
    return this;
  }

  ProfileBuilder withWebsite(String website) {
    this.website = website;
    return this;
  }

  ProfileBuilder withTitle(String title) {
    this.title = title;
    return this;
  }

  ProfileBuilder withBio(String bio) {
    this.bio = bio;
    return this;
  }

  ProfileBuilder withBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    return this;
  }

  ProfileBuilder withServices(LinkedHashSet<String> services) {
    this.services = services;
    return this;
  }

  ProfileBuilder withSocialLinks(LinkedHashSet<SocialDto> socialLinks) {
    this.socialLinks = socialLinks;
    return this;
  }

  ProfileBuilder withLocation(String location) {
    this.location = location;
    return this;
  }

  ProfileBuilder withFollowing(Set<ObjectId> following) {
    this.following = following;
    return this;
  }

  ProfileBuilder withFollowers(Set<ObjectId> followers) {
    this.followers = followers;
    return this;
  }

  @Override
  public Profile build() {
    return new Profile(this);
  }

}
