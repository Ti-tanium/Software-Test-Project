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
import static org.assertj.core.error.ShouldHaveContent.shouldHaveContent;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Files;
import org.assertj.core.internal.FilesBaseTest;
import org.assertj.core.util.FilesException;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for <code>{@link Files#assertHasContent(AssertionInfo, File, String, Charset)}</code>.
 * 
 * @author Olivier Michallat
 * @author Joel Costigliola
 */
public class Files_assertHasContent_Test extends FilesBaseTest {

  private static File actual;
  private static String expected;
  private static Charset charset;

  @BeforeClass
  public static void setUpOnce() {
    // Does not matter if the values differ, the actual comparison is mocked in this test
    actual = new File("src/test/resources/actual_file.txt");
    expected = "xyz";
    charset = Charset.defaultCharset();
  }

  @Test public void should_fail_if_file_does_not_have_expected_text_content() throws IOException{List<String> diffs=newArrayList("line:1, expected:<line1> but was:<EOF>");when(diff.diff(actual,expected,charset)).thenReturn(diffs);AssertionInfo info=someInfo();try {files.assertHasContent(info,actual,expected,charset);} catch (AssertionError e){verify(failures).failure(info,shouldHaveContent(actual,charset,diffs));return;}failBecauseExpectedAssertionErrorWasNotThrown();}
}
