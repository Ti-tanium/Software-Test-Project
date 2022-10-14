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
package org.assertj.core.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;

import java.util.Arrays;

import org.assertj.core.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link org.assertj.core.presentation.HexadecimalRepresentation#toStringOf(Object)}.
 *
 * @author Mariusz Smykula
 */
public class Assertions_assertThat_inHexadecimal_Test {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_assert_doubles_in_hexadecimal(){thrown.expectMessage("expected:<[0x[4008]_0000_0000_0000]> but was:<[0x[3FF0_0000_0000_0000, 0x4000]_0000_0000_0000]>");assertThat(new double[]{1d,2d}).inHexadecimal().isEqualTo(new double[]{3d});}

}
