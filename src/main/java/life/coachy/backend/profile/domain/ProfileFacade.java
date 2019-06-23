package life.coachy.backend.profile.domain;

import com.querydsl.core.types.Predicate;
import java.util.List;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDtoBuilder;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;
import life.coachy.backend.profile.query.ProfileQueryDto;
import life.coachy.backend.profile.query.ProfileQueryRepository;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

public class ProfileFacade {

  private final ProfileCreator profileCreator;
  private final ProfileService profileService;
  private final ProfileQueryRepository queryRepository;
  private final QueryOperationsFactory operationsFactory;

  ProfileFacade(ProfileCreator profileCreator, ProfileService profileService, ProfileQueryRepository queryRepository,
      QueryOperationsFactory operationsFactory) {
    this.profileCreator = profileCreator;
    this.profileService = profileService;
    this.queryRepository = queryRepository;
    this.operationsFactory = operationsFactory;
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

  public List<ProfileQueryDto> fetchAll(Predicate predicate, Pageable pageable) {
    return this.operationsFactory.obtainOperation(predicate, pageable, this.queryRepository);
  }

  private ProfileQueryDto fetchOneOrThrow(ObjectId userId) {
    return this.queryRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

}
