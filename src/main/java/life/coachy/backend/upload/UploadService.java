package life.coachy.backend.upload;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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

    String token = RandomString.make(32);
    String fileName = StringUtils.cleanPath(token + file.getOriginalFilename());
    Path path = targetDirectoryPath.resolve(fileName);

    InputStream inputStream = file.getInputStream();
    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    inputStream.close();

    return ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/resources/" + targetDirectory + "/" + fileName;
  }

  Resource loadAsResource(String fileName, String targetDirectory) throws MalformedURLException {
    Preconditions.checkNotNull(fileName, "File name cannot be null");
    Preconditions.checkNotNull(targetDirectory, "Target directory path cannot be null");

    Path targetDirectoryPath = Paths.get(this.uploadDirectoryPath + File.separator + targetDirectory);
    Path filePath = targetDirectoryPath.resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());

    if (resource.exists()) {
      return resource;
    }

    throw new UploadedFileNotFoundException();
  }


}
