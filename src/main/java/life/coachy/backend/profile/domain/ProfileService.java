package life.coachy.backend.profile.domain;

import life.coachy.backend.profile.query.ProfileQueryDto;
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

    this.save(profile);
  }

}
