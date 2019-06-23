package life.coachy.backend.profile.domain;

import java.util.Set;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.profile.social.dto.SocialDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.PROFILES)
class Profile {

  @Id private ObjectId identifier;
  private ObjectId userId;
  private String website;
  private String title;
  private String bio;
  private String bannerUrl;
  private String location;
  private Set<String> services;
  private Set<SocialDto> socialLinks;
  private Set<ObjectId> followers;
  private Set<ObjectId> following;

  Profile() {}

  Profile(ProfileBuilder builder) {
    this.identifier = builder.identifier;
    this.userId = builder.userId;
    this.website = builder.website;
    this.title = builder.title;
    this.bio = builder.bio;
    this.bannerUrl = builder.bannerUrl;
    this.location = builder.location;
    this.services = builder.services;
    this.socialLinks = builder.socialLinks;
    this.followers = builder.followers;
    this.following = builder.following;
  }

  void setIdentifier(ObjectId identifier) {
    this.identifier = identifier;
  }

  void setUserId(ObjectId userId) {
    this.userId = userId;
  }

  void setFollowers(Set<ObjectId> followers) {
    this.followers = followers;
  }

  void setFollowing(Set<ObjectId> following) {
    this.following = following;
  }

}
