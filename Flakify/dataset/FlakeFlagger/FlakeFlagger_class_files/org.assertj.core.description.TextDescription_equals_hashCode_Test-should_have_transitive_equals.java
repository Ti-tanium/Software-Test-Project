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
package org.assertj.core.description;

import static junit.framework.Assert.assertFalse;
import static org.assertj.core.test.EqualsHashCodeContractAssert.*;

import org.assertj.core.description.TextDescription;
import org.junit.*;

/**
 * Tests for <code>{@link TextDescription#equals(Object)}</code> and <code>{@link TextDescription#hashCode()}</code>.
 * 
 * @author Alex Ruiz
 */
public class TextDescription_equals_hashCode_Test {

  private static TextDescription description;

  @BeforeClass
  public static void setUpOnce() {
    description = new TextDescription("Yoda");
  }

  @Test
  public void should_have_transitive_equals() {
    assertEqualsIsTransitive(description, new TextDescription("Yoda"), new TextDescription("Yoda"));
  }
}
