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
package org.assertj.core.extractor;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.test.Employee;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.Name;
import org.assertj.core.util.introspection.IntrospectionError;
import org.junit.Rule;
import org.junit.Test;

public class ByNameSingleExtractorTest {
  private static final Employee yoda = new Employee(1L, new Name("Yoda"), 800);

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test public void should_throw_exception_if_property_cannot_be_extracted_due_to_runtime_exception_during_property_access() throws Exception{thrown.expect(IntrospectionError.class);Employee employee=brokenEmployee();adultExtractor().extract(employee);}

  private Employee employeeWithBrokenName(String name) {
	return new Employee(1L, new Name(name), 0) {
	  @Override
	  public Name getName() {
		throw new IllegalStateException();
	  }
	};
  }

  private Employee employeeWithOverriddenName(final String overriddenName) {
	return new Employee(1L, new Name("Name"), 0) {
	  @Override
	  public Name getName() {
		return new Name(overriddenName);
	  }
	};
  }

  private Employee brokenEmployee() {
	return new Employee() {
	  @Override
	  public boolean isAdult() {
		throw new IllegalStateException();
	  }
	};
  }

  private ByNameSingleExtractor<Employee> idExtractor() {
	return new ByNameSingleExtractor<>("id");
  }

  private ByNameSingleExtractor<Employee> ageExtractor() {
	return new ByNameSingleExtractor<>("age");
  }

  private ByNameSingleExtractor<Employee> adultExtractor() {
	return new ByNameSingleExtractor<>("adult");
  }

  private ByNameSingleExtractor<Employee> nameExtractor() {
	return new ByNameSingleExtractor<>("name");
  }

}
