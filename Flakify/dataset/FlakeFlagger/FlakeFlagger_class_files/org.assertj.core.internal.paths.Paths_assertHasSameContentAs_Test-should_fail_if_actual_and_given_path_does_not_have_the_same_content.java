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

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.failBecauseExceptionWasNotThrown;
import static org.assertj.core.error.ShouldBeReadable.shouldBeReadable;
import static org.assertj.core.error.ShouldExist.shouldExist;
import static org.assertj.core.error.ShouldHaveSameContent.shouldHaveSameContent;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.error.ShouldHaveSameContent;
import org.assertj.core.internal.Paths;
import org.assertj.core.util.FilesException;
import org.junit.Test;

/**
 * Tests for <code>{@link Paths#assertHasSameContentAs(AssertionInfo, Path, Path)}</code>.
 */
public class Paths_assertHasSameContentAs_Test extends MockPathsBaseTest {

  @Test public void should_fail_if_actual_and_given_path_does_not_have_the_same_content() throws IOException{List<String> diffs=newArrayList("line:1, other:<line1> but was:<EOF>");when(diff.diff(actual.toFile(),other.toFile())).thenReturn(diffs);when(nioFilesWrapper.exists(actual)).thenReturn(true);when(nioFilesWrapper.isReadable(actual)).thenReturn(true);when(nioFilesWrapper.isReadable(other)).thenReturn(true);AssertionInfo info=someInfo();try {paths.assertHasSameContentAs(info,actual,other);} catch (AssertionError e){verify(failures).failure(info,shouldHaveSameContent(actual.toFile(),other.toFile(),diffs));return;}failBecauseExpectedAssertionErrorWasNotThrown();}
}
