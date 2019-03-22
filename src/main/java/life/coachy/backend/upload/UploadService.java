package life.coachy.backend.upload;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import life.coachy.backend.upload.exception.UploadedFileNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
class UploadService {

  @Value("classpath:upload/")
  private Path uploadDirectoryPath;

  String store(MultipartFile file, String targetDirectory) throws IOException {
    Preconditions.checkNotNull(file, "Multipart file cannot be null!");
    Preconditions.checkNotNull(targetDirectory, "Target directory path cannot be null!");

    Path targetDirectoryPath = Paths.get(this.uploadDirectoryPath + File.separator + targetDirectory);
    Files.createDirectories(targetDirectoryPath);

    String fileName = StringUtils.cleanPath(RandomString.make(64) + file.getOriginalFilename());
    this.copyTargetFile(file, targetDirectoryPath, fileName);

    return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/resources/" + targetDirectory + "/" + fileName;
  }

  Resource loadAsResource(String fileName, String targetDirectory) throws MalformedURLException {
    Preconditions.checkNotNull(fileName, "File name cannot be null");
    Preconditions.checkNotNull(targetDirectory, "Target directory path cannot be null");

    Path targetDirectoryPath = Paths.get(this.uploadDirectoryPath + File.separator + targetDirectory);
    Path filePath = targetDirectoryPath.resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());

    return this.orElseThrow(resource);
  }

  private Resource orElseThrow(Resource resource) {
    return Optional.of(resource).orElseThrow(UploadedFileNotFoundException::new);
  }

  private void copyTargetFile(MultipartFile file, Path targetDirectoryPath, String fileName) throws IOException {
    Path path = targetDirectoryPath.resolve(fileName);
    InputStream inputStream = file.getInputStream();
    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    inputStream.close();
  }


}
