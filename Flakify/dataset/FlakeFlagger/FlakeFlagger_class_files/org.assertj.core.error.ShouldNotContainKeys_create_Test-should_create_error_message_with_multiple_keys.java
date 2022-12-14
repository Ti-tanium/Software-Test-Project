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
import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.core.error.ShouldNotContainKeys.shouldNotContainKeys;
import static org.assertj.core.test.Maps.mapOf;
import static org.assertj.core.util.Sets.newLinkedHashSet;

import java.util.Map;

import org.assertj.core.description.TextDescription;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for
 * <code>{@link org.assertj.core.error.ShouldNotContainKeys#create(org.assertj.core.description.Description, org.assertj.core.presentation.Representation)}</code>
 * .
 *
 * @author dorzey
 */
public class ShouldNotContainKeys_create_Test {

  private Map<?, ?> map;
  
  @Before
  public void setUp() {
    map = mapOf(entry("name", "Yoda"), entry("color", "green"));
  }

  @Test
  public void should_create_error_message_with_multiple_keys() {
	ErrorMessageFactory factory = shouldNotContainKeys(map, newLinkedHashSet("name", "color"));
	String message = factory.create(new TextDescription("Test"), new StandardRepresentation());
	assertThat(message).isEqualTo("[Test] \n" +
	                              "Expecting:\n" +
	                              "  <{\"name\"=\"Yoda\", \"color\"=\"green\"}>\n" +
	                              "not to contain keys:\n" +
	                              "  <[\"name\", \"color\"]>");
  }

}
