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
package org.assertj.core.internal.shorts;

import static org.assertj.core.error.ShouldBeBetween.shouldBeBetween;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Shorts;
import org.assertj.core.internal.ShortsBaseTest;
import org.junit.Test;

/**
 * Tests for <code>{@link Shorts#assertIsBetween(AssertionInfo, Short, Short, Short)}</code>.
 * 
 * @author William Delanoue
 */
public class Shorts_assertIsBetween_Test extends ShortsBaseTest {

  private static final Short ZERO = 0;
  private static final Short ONE = 1;
  private static final Short TWO = 2;
  private static final Short TEN = 10;

  @Test(expected = NullPointerException.class)
  public void should_fail_if_end_is_null() {
    shorts.assertIsBetween(someInfo(), ONE, ZERO, null);
  }
}
