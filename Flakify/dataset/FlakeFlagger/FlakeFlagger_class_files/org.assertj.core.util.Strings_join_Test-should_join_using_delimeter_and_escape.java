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

import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;
import static org.assertj.core.util.Lists.newArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for <code>{@link Strings#join(String...)}</code>.
 * 
 * @author Alex Ruiz
 */
public class Strings_join_Test {

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_join_using_delimeter_and_escape(){assertEquals("'Luke'|'Leia'|'Han'",Strings.join("Luke","Leia","Han").with("|","'"));}
}
