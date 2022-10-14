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
import static org.assertj.core.error.ShouldHaveParent.shouldHaveParent;
import static org.assertj.core.test.TestFailures.wasExpectingAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;

import org.assertj.core.util.PathsException;
import org.junit.Before;
import org.junit.Test;

public class Paths_assertHasParent_Test extends MockPathsBaseTest {

  private Path canonicalActual;
  private Path expected;
  private Path canonicalExpected;

  @Before
  public void init() {
	super.init();
	canonicalActual = mock(Path.class);
	expected = mock(Path.class);
	canonicalExpected = mock(Path.class);
  }

  @Test
  public void should_fail_if_actual_parent_is_not_expected_parent() throws IOException {
	final Path actualParent = mock(Path.class);

	when(actual.toRealPath()).thenReturn(canonicalActual);
	when(expected.toRealPath()).thenReturn(canonicalExpected);

	when(canonicalActual.getParent()).thenReturn(actualParent);

	try {
	  paths.assertHasParent(info, actual, expected);
	  wasExpectingAssertionError();
	} catch (AssertionError e) {
	  verify(failures).failure(info, shouldHaveParent(actual, actualParent, expected));
	}
  }
}
