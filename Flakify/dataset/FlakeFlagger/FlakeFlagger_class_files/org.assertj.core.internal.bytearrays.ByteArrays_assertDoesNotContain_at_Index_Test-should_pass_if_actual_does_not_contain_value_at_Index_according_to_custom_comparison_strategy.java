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

import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.error.ShouldNotContainAtIndex.shouldNotContainAtIndex;
import static org.assertj.core.test.ByteArrays.emptyArray;
import static org.assertj.core.test.TestData.*;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.data.Index;
import org.assertj.core.internal.ByteArrays;
import org.assertj.core.internal.ByteArraysBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link ByteArrays#assertDoesNotContain(AssertionInfo, byte[], byte, Index)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class ByteArrays_assertDoesNotContain_at_Index_Test extends ByteArraysBaseTest {

  @Test public void should_pass_if_actual_does_not_contain_value_at_Index_according_to_custom_comparison_strategy(){arraysWithCustomComparisonStrategy.assertDoesNotContain(someInfo(),actual,(byte)6,atIndex(1));}
}
