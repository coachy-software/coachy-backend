package life.coachy.backend.profile.domain;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import life.coachy.backend.infrastructure.query.QueryOperationsFactory;
import life.coachy.backend.notification.domain.NotificationFacade;
import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDtoBuilder;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;
import life.coachy.backend.profile.query.ProfileQueryDto;
import life.coachy.backend.profile.query.ProfileQueryRepository;
import life.coachy.backend.user.domain.UserFacade;
import life.coachy.backend.user.domain.exception.UserNotFoundException;
import life.coachy.backend.user.query.UserQueryDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

public class ProfileFacade {

  private final ProfileCreator profileCreator;
  private final ProfileService profileService;
  private final ProfileQueryRepository queryRepository;
  private final QueryOperationsFactory operationsFactory;
  private final NotificationFacade notificationFacade;
  private final ProfileConverter profileConverter;
  private final UserFacade userFacade;

  ProfileFacade(ProfileCreator profileCreator, ProfileService profileService, ProfileQueryRepository queryRepository, QueryOperationsFactory operationsFactory,
      NotificationFacade notificationFacade, UserFacade userFacade, ProfileConverter profileConverter) {
    this.profileCreator = profileCreator;
    this.profileService = profileService;
    this.queryRepository = queryRepository;
    this.operationsFactory = operationsFactory;
    this.notificationFacade = notificationFacade;
    this.userFacade = userFacade;
    this.profileConverter = profileConverter;
  }

  public void createProfile(ObjectId userId) {
    this.profileService.save(this.profileCreator.from(ProfileCreateCommandDtoBuilder.create().withUserId(userId).build()));
  }

  public void updateProfile(ProfileUpdateCommandDto dto, ObjectId userId) {
    ProfileQueryDto queryDto = this.fetchOneOrThrow(userId);
    this.profileService.update(this.profileCreator.from(dto), queryDto);
  }

  public void toggleFollow(boolean force, ObjectId followingId, ObjectId followerId) {
    ProfileQueryDto followingProfile = this.fetchOneOrThrow(followingId);
    ProfileQueryDto followerProfile = this.fetchOneOrThrow(followerId);

    this.profileService.toggleFollow(force, this.profileCreator.from(followingProfile), followingProfile, followerId);
    this.profileService.toggleFollowing(force, this.profileCreator.from(followerProfile), followerProfile, followingId);
  }

  public void sendNotification(UserQueryDto sender, ObjectId recipientId) {
    this.notificationFacade.sendNotificationToUser(this.profileService.makeNotificationMessage(sender, this.userFacade.fetchOne(recipientId)));
  }

  public Set<UserQueryDto> fetchFollowers(ObjectId id) {
    ProfileQueryDto queryDto = this.fetchOneOrThrow(id);
    Set<UserQueryDto> followers = Sets.newHashSet();

    queryDto.getFollowers().forEach(followerId -> followers.add(this.userFacade.fetchOne(followerId)));
    return followers;
  }

  public Set<UserQueryDto> fetchFollowing(ObjectId id) {
    ProfileQueryDto queryDto = this.fetchOneOrThrow(id);
    Set<UserQueryDto> following = Sets.newHashSet();

    queryDto.getFollowing().forEach(followingUserId -> following.add(this.userFacade.fetchOne(followingUserId)));
    return following;
  }

  public ProfileQueryDto fetchByUserId(ObjectId userId) {
    return this.profileConverter.convert(this.profileService.convertAndAppendUserDetails(this.fetchOneOrThrow(userId), this.userFacade.fetchOne(userId)));
  }

  public List<ProfileQueryDto> fetchAll(Predicate predicate, Pageable pageable) {
    return this.operationsFactory.obtainOperation(predicate, pageable, this.queryRepository).stream()
        .map(profile -> this.profileConverter.convert(this.profileService.convertAndAppendUserDetails(profile, this.userFacade.fetchOne(profile.getUserId()))))
        .collect(Collectors.toList());
  }

  private ProfileQueryDto fetchOneOrThrow(ObjectId userId) {
    return this.queryRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
  }

}
