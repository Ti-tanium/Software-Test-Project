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

import static org.assertj.core.error.ShouldBeExecutable.shouldBeExecutable;
import static org.assertj.core.error.ShouldExist.shouldExist;
import static org.assertj.core.test.TestFailures.wasExpectingAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class Paths_assertIsExecutable_Test extends MockPathsBaseTest {

  @Test public void should_fail_if_actual_exists_but_is_not_executable(){try {when(nioFilesWrapper.exists(actual)).thenReturn(true);when(nioFilesWrapper.isExecutable(actual)).thenReturn(false);paths.assertIsExecutable(info,actual);wasExpectingAssertionError();} catch (AssertionError e){verify(failures).failure(info,shouldBeExecutable(actual));}}

}
