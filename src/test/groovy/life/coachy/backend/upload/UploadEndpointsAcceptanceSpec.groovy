package life.coachy.backend.upload

import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Shared

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UploadEndpointsAcceptanceSpec extends IntegrationSpec {

  @Shared MockMultipartFile pngImageFile = new MockMultipartFile("file", "something.png", "image/png", (byte[]) null)
  @Shared MockMultipartFile mp4ImageFile = new MockMultipartFile("file", "something.mp4", "video/mp4", (byte[]) null)

  def "upload file positive scenario"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I upload file to /api/uploads"
      ResultActions uploadEndpoint = mockMvc.perform(multipart("/api/uploads")
          .file(this.pngImageFile)
          .param("target", "tests")
          .with(httpBasic("yang160", "password123")))
    then: "File uploaded and stored in the system"
      uploadEndpoint.andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
    when: "I go to resource url"
      ResultActions resourceEndpoint = mockMvc.perform(get(uploadEndpoint.andReturn().getResponse().getRedirectedUrl()))
    then:
      resourceEndpoint.andExpect(status().isOk())
          .andExpect(header().exists("Content-Disposition"))
  }

  def "upload file negative scenario"() {
    given: "we have one user in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
    when: "I upload file to /api/uploads"
      ResultActions uploadEndpoint = mockMvc.perform(multipart("/api/uploads")
          .file(this.mp4ImageFile)
          .param("target", "tests")
          .with(httpBasic("yang160", "password123")))
    then: "File uploaded and stored in the system"
      uploadEndpoint.andExpect(status().isUnsupportedMediaType())
  }

}
