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

import org.assertj.core.description.Description;
import org.assertj.core.internal.ComparatorBasedComparisonStrategy;
import org.assertj.core.internal.ComparisonStrategy;
import org.assertj.core.internal.TestDescription;
import org.assertj.core.presentation.StandardRepresentation;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static java.lang.Integer.toHexString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldBeEqual.shouldBeEqual;
import static org.assertj.core.util.Strings.concat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for
 * <code>{@link ShouldBeEqual#newAssertionError(Description, org.assertj.core.presentation.Representation)}</code>.
 * 
 * @author Joel Costigliola (based on Tomasz Nurkiewicz ideas)
 */
public class ShouldBeEqual_newAssertionError_differentiating_expected_and_actual_Test {

  private String formattedDescription = "[my test]";
  private Description description;
  private ShouldBeEqual shouldBeEqual;

  @Before
  public void setUp() {
	description = new TestDescription("my test");
  }

  @Test public void should_create_AssertionError_with_message_differentiating_expected_and_actual_persons(){Person actual=new Person("Jake",43);Person expected=new Person("Jake",47);shouldBeEqual=(ShouldBeEqual)shouldBeEqual(actual,expected,new StandardRepresentation());shouldBeEqual.descriptionFormatter=mock(DescriptionFormatter.class);when(shouldBeEqual.descriptionFormatter.format(description)).thenReturn(formattedDescription);AssertionError error=shouldBeEqual.newAssertionError(description,new StandardRepresentation());assertThat(error.getMessage()).isEqualTo("[my test] \nExpecting:\n <\"Person[name=Jake] (Person@" + toHexString(actual.hashCode()) + ")\">\nto be equal to:\n <\"Person[name=Jake] (Person@" + toHexString(expected.hashCode()) + ")\">\nbut was not.");}

  private static class Person {
	private final String name;
	private final int age;

	public Person(String name, int age) {
	  this.name = name;
	  this.age = age;
	}

	@Override
	public String toString() {
	  return concat("Person[name=", name, "]");
	}
  }

  private static class PersonComparator implements Comparator<Person> {
	@Override
	public int compare(Person p1, Person p2) {
	  return p1.age - p2.age;
	}
  }

}
