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
package org.assertj.core.internal.bytearrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;

import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ByteArraysBaseTest;
import org.junit.Test;


public class ByteArrays_assertHasSameSizeAs_with_Iterable_Test extends ByteArraysBaseTest {

  @Test
  public void should_fail_if_size_of_actual_is_not_equal_to_expected_size() {
    AssertionInfo info = someInfo();
    List<String> other = newArrayList("Solo", "Leia");
    try {
      arrays.assertHasSameSizeAs(info, actual, other);
    } catch (AssertionError e) {
      assertThat(e).hasMessage(shouldHaveSameSizeAs(actual, actual.length, other.size())
          .create(null, info.representation()));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
