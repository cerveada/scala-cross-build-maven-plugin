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

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

class ArtifactIdRewriteRule implements RewriteRule {

  private final String newVersion;

  public ArtifactIdRewriteRule(final String newVersion) {
    this.newVersion = newVersion;
  }

  @Override
  public List<Replacement> replace(final List<String> path, final XMLEventReader reader, final XMLEvent event) throws
      XMLStreamException {
    if (!event.isStartElement()) {
      return Collections.emptyList();
    }
    if (path.size() != 2 || !"project".equals(path.get(0)) || !"artifactId".equals(path.get(1))) {
      return Collections.emptyList();
    }
    final ScalaVersionManipulation svm = new ScalaVersionManipulation();
    final int offset = reader.peek().getLocation().getCharacterOffset();
    final String oldArtifactId = reader.getElementText().trim();
    final String newArtifactId = svm.changeScalaVersionInArtifactId(oldArtifactId, newVersion);
    if (oldArtifactId.equals(newArtifactId)) {
      return Collections.emptyList();
    }

    final byte[] replacement = newArtifactId.getBytes(StandardCharsets.UTF_8);
    final int length = oldArtifactId.getBytes(StandardCharsets.UTF_8).length;
    final Replacement occurrence = new Replacement(offset, length, replacement);
    return Collections.singletonList(occurrence);
  }
}
