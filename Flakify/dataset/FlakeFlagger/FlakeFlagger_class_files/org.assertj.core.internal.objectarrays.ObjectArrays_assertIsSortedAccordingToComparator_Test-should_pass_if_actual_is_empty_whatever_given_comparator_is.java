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
package org.assertj.core.internal.objectarrays;

import static org.assertj.core.error.ShouldBeSorted.shouldBeSortedAccordingToGivenComparator;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.Arrays.array;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import java.util.Comparator;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.ObjectArrays;
import org.assertj.core.internal.ObjectArraysBaseTest;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for <code>{@link ObjectArrays#assertIsSorted(AssertionInfo, Object[])}</code>.
 * 
 * @author Joel Costigliola
 * @author Mikhail Mazursky
 */
public class ObjectArrays_assertIsSortedAccordingToComparator_Test extends ObjectArraysBaseTest {

  private Comparator<String> stringDescendingOrderComparator;
  private Comparator<Object> comparator;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    actual = array("Yoda", "Vador", "Luke", "Luke", "Leia");
    stringDescendingOrderComparator = new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        return -s1.compareTo(s2);
      }
    };
    comparator = new Comparator<Object>() {
      @Override
      public int compare(Object o1, Object o2) {
        return o1.toString().compareTo(o2.toString());
      }
    };
  }

  @Test
  public void should_pass_if_actual_is_empty_whatever_given_comparator_is() {
    arrays.assertIsSortedAccordingToComparator(someInfo(), new String[0], stringDescendingOrderComparator);
    arrays.assertIsSortedAccordingToComparator(someInfo(), new String[0], comparator);
  }
}
