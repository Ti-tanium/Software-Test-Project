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
package org.assertj.core.data;

import static junit.framework.Assert.assertFalse;
import static org.assertj.core.data.Index.atIndex;
import static org.assertj.core.test.EqualsHashCodeContractAssert.*;


import org.assertj.core.data.Index;
import org.junit.*;

/**
 * Tests for {@link Index#equals(Object)} and {@link Index#hashCode()}.
 *
 * @author Alex Ruiz
 */
public class Index_equals_hashCode_Test {
  private static Index index;

  @BeforeClass
  public static void setUpOnce() {
    index = atIndex(8);
  }

  @Test public void should_have_transitive_equals(){assertEqualsIsTransitive(index,atIndex(8),atIndex(8));}
}
