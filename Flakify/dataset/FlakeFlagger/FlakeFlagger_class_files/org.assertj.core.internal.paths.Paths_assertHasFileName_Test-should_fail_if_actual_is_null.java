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
package org.assertj.core.internal.paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.assertj.core.internal.PathsBaseTest;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

public class Paths_assertHasFileName_Test extends PathsBaseTest {

  @ClassRule
  public static FileSystemResource resource = new FileSystemResource();

  private static Path existingFile;
  private static Path symlinkToExistingFile;
  private static Path nonExistingPath;
  private static Path symlinkToNonExistingPath;
  private static Path existingDirectory;
  private static Path symlinkToExistingDirectory;
  
  @BeforeClass
  public static void initPaths() throws IOException {

	final FileSystem fs = resource.getFileSystem();

	existingDirectory = fs.getPath("/dir1/dir2");
	symlinkToExistingDirectory = fs.getPath("/symlinkToExistingDirectory");
	Files.createDirectory(fs.getPath("/dir1"));
	Files.createDirectory(existingDirectory);
	Files.createSymbolicLink(symlinkToExistingDirectory, existingDirectory);

	existingFile = fs.getPath("/dir1/dir2/gc.log");
	symlinkToExistingFile = fs.getPath("/dir1/good-symlink");
	Files.createFile(existingFile);
	Files.createSymbolicLink(symlinkToExistingFile, existingFile);

	nonExistingPath = fs.getPath("/dir1/fake.log");
	symlinkToNonExistingPath = fs.getPath("/dir1/bad-symlink");
	Files.createSymbolicLink(symlinkToNonExistingPath, nonExistingPath);
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	paths.assertHasFileName(info, null, "file.txt");
  }
}
