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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for <code>{@link Assertions#assertThat(File)}</code>.
 * 
 * @author Yvonne Wang
 */
public class Assertions_assertThat_with_File_Test {

  private static File actual;

  @BeforeClass
  public static void setUpOnce() {
    actual = new File("xyz");
  }

  @Test
  public void should_create_Assert() {
    AbstractFileAssert<?> assertions = Assertions.assertThat(actual);
    assertNotNull(assertions);
  }
}
