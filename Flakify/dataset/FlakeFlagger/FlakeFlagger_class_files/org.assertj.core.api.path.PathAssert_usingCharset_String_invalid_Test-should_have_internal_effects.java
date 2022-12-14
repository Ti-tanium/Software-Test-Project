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
package org.assertj.core.api.path;

import static org.assertj.core.test.ExpectedException.none;


import org.assertj.core.api.PathAssert;
import org.assertj.core.api.PathAssertBaseTest;
import org.assertj.core.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link PathAssert#usingCharset(String)}</code> when the provided string is invalid.
 */
public class PathAssert_usingCharset_String_invalid_Test extends PathAssertBaseTest {

  @Rule
  public ExpectedException thrown = none();

  @Override
  @Test
  public void should_have_internal_effects() {
    thrown.expectIllegalArgumentException("Charset:<'Klingon'> is not supported on this system");
    assertions.usingCharset("Klingon");
  }
}
