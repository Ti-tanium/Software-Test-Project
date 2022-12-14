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
package org.assertj.core.internal.throwables;

import static org.assertj.core.error.ShouldHaveRootCauseExactlyInstance.shouldHaveRootCauseExactlyInstance;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ThrowablesBaseTest;
import org.junit.Test;

/**
 * Tests for
 * {@link org.assertj.core.internal.Throwables#assertHasRootCauseExactlyInstanceOf(org.assertj.core.api.AssertionInfo, Throwable, Class)}
 * .
 * 
 * @author Jean-Christophe Gay
 */
public class Throwables_assertHasRootCauseExactlyInstanceOf_Test extends ThrowablesBaseTest {

  private Throwable throwableWithCause = new Throwable(new Exception(new IllegalArgumentException()));

  @Test
  public void should_fail_if_cause_is_not_exactly_instance_of_expected_type() throws Exception {
    AssertionInfo info = someInfo();
    Class<RuntimeException> expectedCauseType = RuntimeException.class;
    try {
      throwables.assertHasRootCauseExactlyInstanceOf(info, throwableWithCause, expectedCauseType);
    } catch (AssertionError err) {
      verify(failures).failure(info, shouldHaveRootCauseExactlyInstance(throwableWithCause, expectedCauseType));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
