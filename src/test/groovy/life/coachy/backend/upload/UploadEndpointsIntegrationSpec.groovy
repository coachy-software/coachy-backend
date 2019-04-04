package life.coachy.backend.upload

import life.coachy.backend.base.IntegrationSpec
import org.bson.types.ObjectId
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UploadEndpointsIntegrationSpec extends IntegrationSpec {

  def "upload endpoint should return 400 when slash character presents in target param"() {
    given: "we have one user and one file in system"
      setUpUser(ObjectId.get(), "yang160", "password123", Collections.emptySet())
      MockMultipartFile jpegImageFile = new MockMultipartFile("file", "something.jpg", "image/jpeg", (byte[]) null)
    when: "I upload file to /api/uploads"
      ResultActions uploadEndpoint = mockMvc.perform(multipart("/api/uploads")
          .file(jpegImageFile)
          .param("target", "/tests")
          .with(httpBasic("yang160", "password123")))
    then:
      uploadEndpoint.andExpect(status().isBadRequest())
  }

  def "resource endpoint should return 404 when file isn't present"() {
    when: "I go to random resource url"
      ResultActions resourceEndpoint = mockMvc.perform(get("/api/resource/layerName/random.png"))
    then:
      resourceEndpoint.andExpect(status().isNotFound())
  }

}
