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
import life.coachy.backend.util.security.RequiresAuthenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
      @ApiResponse(code = 415, message = "Extension not allowed"),
      @ApiResponse(code = 200, message = "File uploaded and stored")
  })
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @RequiresAuthenticated
  public ResponseEntity<Map<String, String>> upload(
      @RequestPart @ApiParam("File to upload") MultipartFile file,
      @RequestParam @ApiParam("Directory path to store uploading file") String target) throws IOException {
    if (!Arrays.asList("jpg", "png", "jpeg").contains(FilenameUtil.getExtension(file.getOriginalFilename()))) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    return ResponseEntity.ok(Collections.singletonMap("fileUrl", this.uploadService.store(file, target)));
  }

  @ApiOperation("Displays specified file")
  @ApiResponses({
      @ApiResponse(code = 404, message = "File cannot be found"),
      @ApiResponse(code = 200, message = "File found and displayed")
  })
  @GetMapping
  @RequiresAuthenticated
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
