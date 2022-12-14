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
package org.assertj.core.internal.booleanarrays;

import static org.assertj.core.error.ShouldBeSorted.shouldBeSortedAccordingToGivenComparator;
import static org.assertj.core.test.BooleanArrays.emptyArray;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import java.util.Comparator;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.BooleanArrays;
import org.assertj.core.internal.BooleanArraysBaseTest;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for <code>{@link BooleanArrays#assertIsSortedAccordingToComparator(AssertionInfo, boolean[], Comparator)}</code>
 * 
 * @author Joel Costigliola
 */
public class BooleanArrays_assertIsSortedAccordingToComparator_Test extends BooleanArraysBaseTest {

  private Comparator<Boolean> booleanDescendingOrderComparator;
  private Comparator<Boolean> booleanAscendingOrderComparator;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    actual = new boolean[] { true, true, false, false };
    booleanDescendingOrderComparator = new Comparator<Boolean>() {
      @Override
      public int compare(Boolean boolean1, Boolean boolean2) {
        return -boolean1.compareTo(boolean2);
      }
    };
    booleanAscendingOrderComparator = new Comparator<Boolean>() {
      @Override
      public int compare(Boolean boolean1, Boolean boolean2) {
        return boolean1.compareTo(boolean2);
      }
    };
  }

  @Test
  public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(actualIsNull());
    arrays.assertIsSortedAccordingToComparator(someInfo(), null, booleanDescendingOrderComparator);
  }

}
