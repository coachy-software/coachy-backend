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
import life.coachy.backend.infrastructure.authentication.RequiresAuthenticated;
import life.coachy.backend.infrastructure.util.FilenameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
  @RequiresAuthenticated
  @PostMapping(value = "/api/uploads", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Map<String, String>> upload(
      @RequestPart @ApiParam("File to upload") MultipartFile file,
      @RequestParam @ApiParam("Directory path to store uploading file") String target
  ) throws IOException {
    if (!Arrays.asList("jpg", "png", "jpeg").contains(FilenameUtil.getExtension(file.getOriginalFilename()))) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    if (target.contains("/")) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(Collections.singletonMap("fileUrl", this.uploadService.store(file, target)));
  }

  @ApiOperation("Displays specified file")
  @ApiResponses({
      @ApiResponse(code = 404, message = "File cannot be found"),
      @ApiResponse(code = 200, message = "File found and displayed")
  })
  @GetMapping("/resources/{layerName}/{fileName}")
  public ResponseEntity<Resource> download(
      @PathVariable @ApiParam("File to display") String fileName,
      @PathVariable @ApiParam("Directory path where file is stored") String layerName,
      HttpServletRequest request
  ) throws IOException {
    Resource resource = this.uploadService.loadAsResource(fileName, layerName);
    String contentType = this.getContentType(request, resource);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }

  private String getContentType(HttpServletRequest request, Resource resource) throws IOException {
    String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return contentType;
  }

}
