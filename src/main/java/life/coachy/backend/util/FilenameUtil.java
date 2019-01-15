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
