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
package org.assertj.core.internal.floats;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;
import static org.assertj.core.error.ShouldBeEqualWithinOffset.shouldBeEqual;
import static org.assertj.core.test.ErrorMessages.offsetIsNull;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;


import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.data.Offset;
import org.assertj.core.internal.Floats;
import org.assertj.core.internal.FloatsBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Floats#assertEqual(AssertionInfo, Float, float, Offset)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Floats_assertEqual_float_with_offset_Test extends FloatsBaseTest {

  @Test public void should_fail_if_second_float_is_null_but_not_the_first(){AssertionInfo info=someInfo();Offset<Float> offset=offset(1f);try {floats.assertEqual(info,6f,null,offset);shouldHaveThrown(NullPointerException.class);} catch (NullPointerException e){assertThat(e).hasMessage("The given number should not be null");}}
}
