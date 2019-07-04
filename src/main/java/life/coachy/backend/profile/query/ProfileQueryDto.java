package life.coachy.backend.profile.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.LinkedHashSet;
import java.util.Set;
import life.coachy.backend.infrastructure.constant.MongoCollections;
import life.coachy.backend.infrastructure.query.QueryDtoMarker;
import life.coachy.backend.profile.social.dto.SocialDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoCollections.PROFILES)
public class ProfileQueryDto implements QueryDtoMarker {

  @Id @JsonSerialize(using = ToStringSerializer.class) private ObjectId identifier;
  @JsonSerialize(using = ToStringSerializer.class) private ObjectId userId;
  @Transient private String username;
  @Transient private String displayName;
  @Transient private String avatar;
  private String website;
  private String title;
  private String bio;
  private String bannerUrl;
  private String location;
  private LinkedHashSet<String> services;
  private LinkedHashSet<SocialDto> socialLinks;
  @JsonSerialize(contentUsing = ToStringSerializer.class) private Set<ObjectId> followers;
  @JsonSerialize(contentUsing = ToStringSerializer.class) private Set<ObjectId> following;

  public ObjectId getIdentifier() {
    return this.identifier;
  }

  public ObjectId getUserId() {
    return this.userId;
  }

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getAvatar() {
    return this.avatar;
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

  public LinkedHashSet<String> getServices() {
    return this.services;
  }

  public LinkedHashSet<SocialDto> getSocialLinks() {
    return this.socialLinks;
  }

  public Set<ObjectId> getFollowers() {
    return this.followers;
  }

  public Set<ObjectId> getFollowing() {
    return this.following;
  }

}
