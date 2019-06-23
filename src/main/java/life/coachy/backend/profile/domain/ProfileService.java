package life.coachy.backend.profile.domain;

import com.google.common.collect.Maps;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import life.coachy.backend.infrastructure.converter.ObjectToJsonConverter;
import life.coachy.backend.notification.domain.dto.NotificationMessageDto;
import life.coachy.backend.notification.domain.dto.NotificationMessageDtoBuilder;
import life.coachy.backend.profile.query.ProfileQueryDto;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProfileService {

  private final ProfileRepository profileRepository;
  private final ObjectToJsonConverter toJsonConverter;

  @Autowired
  ProfileService(ProfileRepository profileRepository, ObjectToJsonConverter toJsonConverter) {
    this.profileRepository = profileRepository;
    this.toJsonConverter = toJsonConverter;
  }

  void save(Profile profile) {
    this.profileRepository.save(profile);
  }

  void update(Profile profile, ProfileQueryDto queryDto) {
    profile.setIdentifier(queryDto.getIdentifier());
    profile.setUserId(queryDto.getUserId());
    profile.setFollowers(queryDto.getFollowers());
    profile.setFollowing(queryDto.getFollowing());

    this.save(profile);
  }

  NotificationMessageDto makeNotificationMessage(UserQueryDto sender, UserQueryDto recipient) {
    return NotificationMessageDtoBuilder.create()
        .withSenderName(sender.getUsername())
        .withSenderAvatar(sender.getAvatar())
        .withSenderId(sender.getIdentifier())
        .withType("ALERT")
        .withContent(this.toJsonConverter.convert(Maps.newHashMap(new HashMap<String, String>() {{
          this.put("link", "/profiles/" + sender.getIdentifier());
          this.put("text", "followed");
        }})))
        .withRecipientId(recipient.getIdentifier())
        .withCreatedAt(LocalDateTime.now())
        .build();
  }

  void toggleFollowing(boolean force, Profile followerProfile, ProfileQueryDto followerQueryDto, ObjectId followingId) {
    Set<ObjectId> following = followerQueryDto.getFollowing();

    if (force) {
      following.add(followingId);
    } else {
      following.removeIf(id -> id.equals(followingId));
    }

    followerProfile.setFollowing(following);
    this.save(followerProfile);
  }

  void toggleFollow(boolean force, Profile followingProfile, ProfileQueryDto followingQueryDto, ObjectId followerId) {
    Set<ObjectId> followers = followingQueryDto.getFollowers();

    if (force) {
      followers.add(followerId);
    } else {
      followers.removeIf(id -> id.equals(followerId));
    }

    followingProfile.setFollowers(followers);
    this.save(followingProfile);
  }

}
