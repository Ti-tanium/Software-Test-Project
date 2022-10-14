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
package org.assertj.core.internal.intarrays;

import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.test.IntArrays.emptyArray;
import static org.assertj.core.test.TestData.*;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.data.Index;
import org.assertj.core.internal.IntArrays;
import org.assertj.core.internal.IntArraysBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link IntArrays#assertDoesNotContain(AssertionInfo, int[], int, Index)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class IntArrays_assertDoesNotContain_at_Index_Test extends IntArraysBaseTest {

  @Test
  public void should_fail_if_actual_contains_value_at_index() {
    AssertionInfo info = someInfo();
    Index index = atIndex(0);
    try {
      arrays.assertDoesNotContain(info, actual, 6, index);
    } catch (AssertionError e) {
      verify(failures).failure(info, shouldNotContainAtIndex(actual, 6, index));
      return;
    }
    failBecauseExpectedAssertionErrorWasNotThrown();
  }
}
