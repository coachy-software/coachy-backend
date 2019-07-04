package life.coachy.backend.profile

import com.google.common.collect.ImmutableSet
import com.google.common.collect.Sets
import groovy.transform.CompileStatic
import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDto
import life.coachy.backend.profile.domain.dto.ProfileCreateCommandDtoBuilder
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDto
import life.coachy.backend.profile.domain.dto.ProfileUpdateCommandDtoBuilder
import life.coachy.backend.profile.social.dto.SocialDto
import org.bson.types.ObjectId

@CompileStatic
trait SampleProfiles {

  ObjectId profileSampleId = ObjectId.get()
  ObjectId secondProfileSampleId = ObjectId.get()

  ProfileCreateCommandDto profileCreateDtoSample = ProfileCreateCommandDtoBuilder.create()
      .withUserId(ObjectId.get())
      .withWebsite("website")
      .withTitle("title")
      .withBio("bio")
      .withBannerUrl("banner-url")
      .withLocation("warsaw")
      .withServices(Sets.newLinkedHashSet(ImmutableSet.of("bodybuilding", "powerbuilding")))
      .withSocialLinks(Sets.newLinkedHashSet(ImmutableSet.of(new SocialDto("linkedin", "#", "fa fa-linkedin"))))
      .build();

  ProfileUpdateCommandDto profileUpdateDtoSample = ProfileUpdateCommandDtoBuilder.create()
      .withWebsite("updated website")
      .withTitle("title")
      .withBio("bio")
      .withBannerUrl("banner-url")
      .withLocation("warsaw")
      .withServices(Sets.newLinkedHashSet(ImmutableSet.of("bodybuilding", "powerbuilding")))
      .withSocialLinks(Sets.newLinkedHashSet(ImmutableSet.of(new SocialDto("linkedin", "#", "fa fa-linkedin"))))
      .build();

}
