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

import static org.assertj.core.test.TestData.someInfo;

import static org.junit.Assert.assertEquals;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Shorts;
import org.assertj.core.internal.ShortsBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Shorts#assertIsNegative(AssertionInfo, Short)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Shorts_assertIsZero_Test extends ShortsBaseTest {

  @Test
  public void should_fail_since_actual_is_not_zero() {
    try {
      shorts.assertIsZero(someInfo(), (short) 2);
    } catch (AssertionError e) {
      assertEquals(e.getMessage(), "expected:<[0]> but was:<[2]>");
    }
  }

}
