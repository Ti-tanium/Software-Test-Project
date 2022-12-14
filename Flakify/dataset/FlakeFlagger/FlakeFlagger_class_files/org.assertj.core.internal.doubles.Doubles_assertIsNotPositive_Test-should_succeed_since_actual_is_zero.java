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
package org.assertj.core.internal.doubles;

import static org.assertj.core.test.TestData.someInfo;

import org.assertj.core.internal.DoublesBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link org.assertj.core.internal.Doubles#assertIsNotPositive(org.assertj.core.api.AssertionInfo, Comparable)}</code>.
 * 
 * @author Nicolas François
 */
public class Doubles_assertIsNotPositive_Test extends DoublesBaseTest {

  @Test
  public void should_succeed_since_actual_is_zero() {
    doubles.assertIsNotPositive(someInfo(), 0d);
  }

}
