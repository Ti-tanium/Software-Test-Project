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
package org.assertj.core.internal.shortarrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.test.ShortArrays.arrayOf;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ShortArraysBaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShortArrays_assertHasSameSizeAs_with_Array_Test extends ShortArraysBaseTest {

  private static short[] actual;

  @BeforeClass
  public static void setUpOnce() {
    // don't use a static import here, it leads to a compilation error with oracle jdk 1.7.0_05 compiler due to the
    // other array static import.
    actual = arrayOf(6, 8);
  }

  @Test
  public void should_fail_if_size_of_actual_is_not_equal_to_expected_size() {
    AssertionInfo info = someInfo();
    String[] other = array("Solo", "Leia", "Yoda");
    try {
      arrays.assertHasSameSizeAs(info, actual, other);
    } catch (AssertionError e) {
      assertThat(e).hasMessage(shouldHaveSameSizeAs(actual, actual.length, other.length)
          .create(null, info.representation()));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
