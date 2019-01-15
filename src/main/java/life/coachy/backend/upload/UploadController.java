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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import life.coachy.backend.util.FilenameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/uploads")
@RestController
class UploadController {

  private UploadService uploadService;

  @Autowired
  public UploadController(UploadService uploadService) {
    this.uploadService = uploadService;
  }

  @ApiOperation("Uploades and then stores files")
  @ApiResponses({
      @ApiResponse(code = 400, message = "Extension not allowed"),
      @ApiResponse(code = 200, message = "File uploaded and stored")
  })
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Map<String, String>> upload(
      @RequestPart @ApiParam("File to upload") MultipartFile file,
      @RequestPart @ApiParam("Directory path to store uploading file") String target) throws IOException {
    if (!Arrays.asList("jpg", "png", "jpeg").contains(FilenameUtil.getExtension(file.getOriginalFilename()))) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(Collections.singletonMap("fileUrl", this.uploadService.store(file, target)));
  }

  @ApiOperation("Displays specified file")
  @ApiResponses({
      @ApiResponse(code = 404, message = "File cannot be found"),
      @ApiResponse(code = 200, message = "File found and displayed")
  })
  @GetMapping
  public ResponseEntity<Resource> download(
      @RequestParam @ApiParam("File to display") String file,
      @RequestParam @ApiParam("Directory path where file is stored") String target,
      HttpServletRequest request) throws IOException {
    Resource resource = this.uploadService.loadAsResource(file, target);
    String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

}
