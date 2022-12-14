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
package org.assertj.core.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldHaveSize.shouldHaveSize;
import static org.assertj.core.util.Lists.newArrayList;

import org.assertj.core.description.*;
import org.assertj.core.presentation.HexadecimalRepresentation;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.*;

/**
 * Tests for <code>{@link ShouldHaveSize#create(org.assertj.core.description.Description, org.assertj.core.presentation.Representation)}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class ShouldHaveSize_create_Test {

  private ErrorMessageFactory factory;

  @Before
  public void setUp() {
    factory = shouldHaveSize(newArrayList('a', 'b'), 4, 2);
  }

  @Test
  public void should_create_error_message() {
    String message = factory.create(new TextDescription("Test"), new StandardRepresentation());
    assertThat(message).isEqualTo("[Test] \nExpected size:<2> but was:<4> in:\n<['a', 'b']>");
  }
}
