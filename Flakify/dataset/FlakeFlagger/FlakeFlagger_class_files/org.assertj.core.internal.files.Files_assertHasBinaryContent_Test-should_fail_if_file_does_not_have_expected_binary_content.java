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
package org.assertj.core.internal.files;

import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;

import static org.assertj.core.error.ShouldBeFile.shouldBeFile;
import static org.assertj.core.error.ShouldHaveBinaryContent.shouldHaveBinaryContent;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BinaryDiffResult;
import org.assertj.core.internal.Files;
import org.assertj.core.internal.FilesBaseTest;
import org.assertj.core.util.FilesException;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for <code>{@link Files#assertHasBinaryContent(org.assertj.core.core.WritableAssertionInfo, File, byte[])}</code>.
 * 
 * @author Olivier Michallat
 * @author Joel Costigliola
 */
public class Files_assertHasBinaryContent_Test extends FilesBaseTest {

  private static File actual;
  private static byte[] expected;

  @BeforeClass
  public static void setUpOnce() {
    // Does not matter if the values differ, the actual comparison is mocked in this test
    actual = new File("src/test/resources/actual_file.txt");
    expected = new byte[] {};
  }

  @Test public void should_fail_if_file_does_not_have_expected_binary_content() throws IOException{BinaryDiffResult diff=new BinaryDiffResult(15,(byte)0xCA,(byte)0xFE);when(binaryDiff.diff(actual,expected)).thenReturn(diff);AssertionInfo info=someInfo();try {files.assertHasBinaryContent(info,actual,expected);} catch (AssertionError e){verify(failures).failure(info,shouldHaveBinaryContent(actual,diff));return;}failBecauseExpectedAssertionErrorWasNotThrown();}
}
