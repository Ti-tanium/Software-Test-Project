/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.util;

import static java.io.File.separator;
import static org.assertj.core.util.Lists.newArrayList;
import static org.assertj.core.util.Strings.concat;
import static org.junit.Assert.*;
import static org.junit.rules.ExpectedException.none;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for <code>{@link Files#fileNamesIn(String, boolean)}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Files_fileNamesIn_Test extends Files_TestCase {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_return_names_of_files_in_given_directory_and_its_subdirectories(){String path=concat("root",separator,"dir_1");assertThatContainsFiles(newArrayList("file_1_1","file_1_2","file_1_1_1"),Files.fileNamesIn(path,true));}

  private void assertThatContainsFiles(List<String> expectedFiles, List<String> actualFiles) {
    assertThereAreNoDuplicates(actualFiles);
    for (String fileName : actualFiles) {
      assertTrue(expectedFiles.remove(pathNameFor(fileName)));
    }
    assertTrue(expectedFiles.isEmpty());
  }

  private String pathNameFor(String fileName) {
    return new File(fileName).getName();
  }

  private void assertThereAreNoDuplicates(List<String> actualFiles) {
    if (actualFiles == null || actualFiles.isEmpty()) {
      return;
    }
    HashSet<String> withoutDuplicates = new HashSet<>(actualFiles);
    assertEquals(withoutDuplicates.size(), actualFiles.size());
  }
}
