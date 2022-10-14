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
package org.assertj.core.api.array;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.error.ShouldHaveSameSizeAs.shouldHaveSameSizeAs;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import org.junit.Rule;
import org.junit.Test;

import org.assertj.core.test.ExpectedException;


/**
 * Tests for <code>{@link org.assertj.core.api.AbstractEnumerableAssert#hasSameSizeAs(Object)}</code>.
 *
 * @author Joel Costigliola
 */
public class AbstractEnumerableAssert_hasSameSizeAs_with_Array_Test {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_pass_if_actual_primitive_array_has_same_size_as_other_primitive_array(){assertThat(new byte[]{1,2}).hasSameSizeAs(new byte[]{2,3});assertThat(new byte[]{1,2}).hasSameSizeAs(new int[]{2,3});}

}
