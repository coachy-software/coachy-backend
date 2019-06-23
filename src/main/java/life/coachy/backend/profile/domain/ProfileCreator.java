package life.coachy.backend.profile.domain;

import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDto;
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto;

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
        .build();
  }

}
