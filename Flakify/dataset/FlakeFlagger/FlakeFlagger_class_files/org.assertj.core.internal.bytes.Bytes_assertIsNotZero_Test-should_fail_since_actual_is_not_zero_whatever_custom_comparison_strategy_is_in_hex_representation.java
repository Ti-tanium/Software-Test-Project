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
package org.assertj.core.internal.bytes;

import static org.assertj.core.test.TestData.someHexInfo;
import static org.assertj.core.test.TestData.someInfo;

import static org.junit.Assert.assertEquals;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Bytes;
import org.assertj.core.internal.BytesBaseTest;
import org.junit.Test;


/**
 * Tests for <code>{@link Bytes#assertIsNotZero(AssertionInfo, Byte)}</code>.
 * 
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Bytes_assertIsNotZero_Test extends BytesBaseTest {

  @Test public void should_fail_since_actual_is_not_zero_whatever_custom_comparison_strategy_is_in_hex_representation(){try {bytesWithAbsValueComparisonStrategy.assertIsNotZero(someHexInfo(),(byte)0x00);} catch (AssertionError e){assertEquals(e.getMessage(),"\nExpecting:\n <0x00>\nnot to be equal to:\n <0x00>\n");}}

}
