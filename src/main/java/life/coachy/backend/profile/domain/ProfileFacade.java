package life.coachy.backend.profile.domain;

import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDtoBuilder;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;
import life.coachy.backend.profile.query.ProfileQueryDto;
import life.coachy.backend.profile.query.ProfileQueryRepository;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import org.bson.types.ObjectId;

public class ProfileFacade {

  private final ProfileCreator profileCreator;
  private final ProfileService profileService;
  private final ProfileQueryRepository queryRepository;

  ProfileFacade(ProfileCreator profileCreator, ProfileService profileService, ProfileQueryRepository queryRepository) {
    this.profileCreator = profileCreator;
    this.profileService = profileService;
    this.queryRepository = queryRepository;
  }

  public void createProfile(ObjectId userId) {
    this.profileService.save(this.profileCreator.from(ProfileCreateCommandDtoBuilder.create().withUserId(userId).build()));
  }

  public void updateProfile(ProfileUpdateCommandDto dto, ObjectId userId) {
    ProfileQueryDto queryDto = this.fetchOneOrThrow(userId);
    this.profileService.update(this.profileCreator.from(dto), queryDto);
  }

  public ProfileQueryDto fetchByUserId(ObjectId userId) {
    return this.fetchOneOrThrow(userId);
  }

  private ProfileQueryDto fetchOneOrThrow(ObjectId userId) {
    return this.queryRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

}
