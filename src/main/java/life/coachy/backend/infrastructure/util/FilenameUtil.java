package life.coachy.backend.infrastructure.util;

/*
  @source https://github.com/apache/commons-io/blob/master/src/main/java/org/apache/commons/io/FilenameUtils.java
 */

import com.google.common.base.Preconditions;

public final class FilenameUtil {


  private FilenameUtil() {
  }

  public static String getExtension(String filename) {
    Preconditions.checkNotNull(filename, "File name cannot be null");

    int index = indexOfExtension(filename);
    return index == -1 ? "" : filename.substring(index + 1);
  }

  private static int indexOfExtension(String filename) {
    Preconditions.checkNotNull(filename, "File name cannot be null");

    int extensionPos = filename.lastIndexOf(46);
    int lastSeparator = indexOfLastSeparator(filename);
    return lastSeparator > extensionPos ? -1 : extensionPos;
  }

  private static int indexOfLastSeparator(String filename) {
    Preconditions.checkNotNull(filename, "File name cannot be null");

    int lastUnixPos = filename.lastIndexOf(47);
    int lastWindowsPos = filename.lastIndexOf(92);
    return Math.max(lastUnixPos, lastWindowsPos);
  }

}
