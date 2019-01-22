package life.coachy.backend.util;

/*
  @source https://github.com/apache/commons-io/blob/master/src/main/java/org/apache/commons/io/FilenameUtils.java
 */

public final class FilenameUtil {


  private FilenameUtil() {
  }

  public static String getExtension(String filename) {
    if (filename == null) {
      return null;
    } else {
      int index = indexOfExtension(filename);
      return index == -1 ? "" : filename.substring(index + 1);
    }
  }

  private static int indexOfExtension(String filename) {
    if (filename == null) {
      return -1;
    } else {
      int extensionPos = filename.lastIndexOf(46);
      int lastSeparator = indexOfLastSeparator(filename);
      return lastSeparator > extensionPos ? -1 : extensionPos;
    }
  }

  private static int indexOfLastSeparator(String filename) {
    if (filename == null) {
      return -1;
    } else {
      int lastUnixPos = filename.lastIndexOf(47);
      int lastWindowsPos = filename.lastIndexOf(92);
      return Math.max(lastUnixPos, lastWindowsPos);
    }
  }

}
