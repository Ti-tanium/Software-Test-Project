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
package org.assertj.core.internal.bytes;

import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.error.ShouldBeEqualWithinOffset.shouldBeEqual;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BytesBaseTest;
import org.junit.Test;

public class Bytes_assertIsCloseTo_Test extends BytesBaseTest {

  private static final Byte ZERO = 0;
  private static final Byte ONE = 1;
  private static final Byte TWO = 2;
  private static final Byte TEN = 10;

  @Test public void should_pass_if_difference_is_equal_to_given_offset(){bytes.assertIsCloseTo(someInfo(),ONE,ONE,within(ZERO));bytes.assertIsCloseTo(someInfo(),ONE,ZERO,within(ONE));bytes.assertIsCloseTo(someInfo(),ONE,TWO,within(ONE));}
}
