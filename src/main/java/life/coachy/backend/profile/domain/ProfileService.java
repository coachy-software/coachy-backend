package life.coachy.backend.profile.domain;

import java.util.Set;
import life.coachy.backend.profile.query.ProfileQueryDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProfileService {

  private final ProfileRepository profileRepository;

  @Autowired
  ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
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
