/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.mojo.scala.crossbuild;

import java.util.Arrays;
import java.util.Locale;

class Replacement {
  public final long offset;
  public final long length;
  public final byte[] replacement;

  public Replacement(final long offset, final long length, final byte[] replacement) {
    this.offset = offset;
    this.length = length;
    this.replacement = replacement;
  }

  @Override
  public String toString() {
    return String.format(
        Locale.ENGLISH,
        "Replacement(offset=%d, length=%d, replacement=%s",
        offset,
        length,
        new String(replacement)
    );
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Replacement that = (Replacement) o;

    if (offset != that.offset)
      return false;
    if (length != that.length)
      return false;
    return Arrays.equals(replacement, that.replacement);

  }

  @Override public int hashCode() {
    int result = (int) (offset ^ (offset >>> 32));
    result = 31 * result + (int) (length ^ (length >>> 32));
    result = 31 * result + Arrays.hashCode(replacement);
    return result;
  }
}
