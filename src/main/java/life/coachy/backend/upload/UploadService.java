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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    Path targetDirectoryPath = Paths.get(this.uploadDirectoryPath + targetDirectory);
    Files.createDirectories(targetDirectoryPath);

    String token = RandomString.make(32);
    String fileName = StringUtils.cleanPath(token + file.getOriginalFilename());
    Path path = targetDirectoryPath.resolve(fileName);

    InputStream inputStream = file.getInputStream();
    Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
    inputStream.close();

    return ServletUriComponentsBuilder.fromCurrentRequestUri()
        .queryParam("target", targetDirectory)
        .queryParam("file", fileName)
        .toUriString();

  }

  Resource loadAsResource(String fileName, String targetDirectory) throws MalformedURLException {
    Path targetDirectoryPath = Paths.get(this.uploadDirectoryPath + targetDirectory);
    Path filePath = targetDirectoryPath.resolve(fileName).normalize();
    Resource resource = new UrlResource(filePath.toUri());

    if (resource.exists()) {
      return resource;
    }

    throw new UploadedFileNotFoundException();
  }


}
