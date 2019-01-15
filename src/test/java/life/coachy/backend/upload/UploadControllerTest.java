/*
 * MIT License
 *
 * Copyright (c) 2018 Coachy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package life.coachy.backend.upload;


import de.flapdoodle.embed.process.io.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UploadControllerTest {

  @Value("classpath:upload/")
  private Path uploadDirectoryPath;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;
  private MockMultipartFile pngImageFile;
  private MockMultipartFile jpegImageFile;
  private MockMultipartFile mp4ImageFile;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    this.pngImageFile = new MockMultipartFile("file", "something.png", "image/png", (byte[]) null);
    this.jpegImageFile = new MockMultipartFile("file", "something.jpg", "image/jpeg", (byte[]) null);
    this.mp4ImageFile = new MockMultipartFile("file", "something.mp4", "video/mp4", (byte[]) null);
  }

  @Test
  public void pngImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.pngImageFile)
        .param("target", "/tests"))
        .andExpect(status().is(200));
  }

  @Test
  public void jpegImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.jpegImageFile)
        .param("target", "/tests"))
        .andExpect(status().is(200));
  }

  @Test
  public void mp4ImageUploadTest() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.mp4ImageFile)
        .param("target", "/tests"))
        .andExpect(status().is(415));
  }

  @Test
  public void downloadPngImageTest() throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.pngImageFile)
        .param("target", "/tests"))
        .andDo(result -> stringBuilder.append(result.getResponse().getContentAsString()))
        .andExpect(status().is(200));

    String fileUrl = new JSONObject(stringBuilder.toString()).get("fileUrl").toString();

    this.mockMvc.perform(MockMvcRequestBuilders.get(fileUrl.substring(fileUrl.lastIndexOf("/api"))))
        .andExpect(status().isOk());
  }

  @Test
  public void downloadJpegImageTest() throws Exception {
    StringBuilder stringBuilder = new StringBuilder();

    this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/uploads")
        .file(this.jpegImageFile)
        .param("target", "/tests"))
        .andDo(result -> stringBuilder.append(result.getResponse().getContentAsString()))
        .andExpect(status().is(200));

    String fileUrl = new JSONObject(stringBuilder.toString()).get("fileUrl").toString();

    this.mockMvc.perform(MockMvcRequestBuilders.get(fileUrl.substring(fileUrl.lastIndexOf("/api"))))
        .andExpect(status().isOk());
  }

  @Test
  public void shouldFailIfFileDoesNotExists() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/uploads/?file=doesntExists.png&target=/tests"))
        .andExpect(status().isNotFound());
  }

  @After
  public void tearDown() throws Exception {
    Path path = Paths.get(this.uploadDirectoryPath + "/tests");
    if (java.nio.file.Files.exists(path)) {
      Files.forceDelete(Paths.get(this.uploadDirectoryPath + "/tests"));
    }
  }

}
