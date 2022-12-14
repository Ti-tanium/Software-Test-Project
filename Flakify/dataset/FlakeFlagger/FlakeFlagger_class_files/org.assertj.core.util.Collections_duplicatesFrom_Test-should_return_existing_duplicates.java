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
package org.assertj.core.util;

import static java.util.Arrays.asList;
import static org.assertj.core.util.Collections.duplicatesFrom;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

/**
 * Tests for {@link Collections#duplicatesFrom(Collection)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Collections_duplicatesFrom_Test {
  @Test
  public void should_return_existing_duplicates() {
    Collection<String> duplicates = duplicatesFrom(asList("Merry", "Frodo", "Merry", "Sam", "Frodo"));
    assertArrayEquals(new String[] { "Merry", "Frodo" }, duplicates.toArray());
  }
}
