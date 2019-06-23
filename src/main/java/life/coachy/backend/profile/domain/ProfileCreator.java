package life.coachy.backend.profile.domain;

import com.google.common.collect.Sets;
import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDto;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;
import life.coachy.backend.profile.query.ProfileQueryDto;

class ProfileCreator {

  Profile from(ProfileUpdateCommandDto dto) {
    return ProfileBuilder.create()
        .withWebsite(dto.getWebsite())
        .withTitle(dto.getTitle())
        .withBio(dto.getBio())
        .withBannerUrl(dto.getBannerUrl())
        .withLocation(dto.getLocation())
        .withServices(dto.getServices())
        .withSocialLinks(dto.getSocialLinks())
        .build();
  }

  Profile from(ProfileCreateCommandDto dto) {
    return ProfileBuilder.create()
        .withUserId(dto.getUserId())
        .withWebsite(dto.getWebsite())
        .withTitle(dto.getTitle())
        .withBio(dto.getBio())
        .withBannerUrl(dto.getBannerUrl())
        .withLocation(dto.getLocation())
        .withServices(dto.getServices())
        .withSocialLinks(dto.getSocialLinks())
        .withFollowers(Sets.newHashSet())
        .withFollowing(Sets.newHashSet())
        .build();
  }

  Profile from(ProfileQueryDto dto) {
    return ProfileBuilder.create()
        .withIdentifier(dto.getIdentifier())
        .withUserId(dto.getUserId())
        .withWebsite(dto.getWebsite())
        .withTitle(dto.getTitle())
        .withBio(dto.getBio())
        .withBannerUrl(dto.getBannerUrl())
        .withLocation(dto.getLocation())
        .withServices(dto.getServices())
        .withSocialLinks(dto.getSocialLinks())
        .withFollowers(dto.getFollowers())
        .withFollowing(dto.getFollowing())
        .build();
  }

}
