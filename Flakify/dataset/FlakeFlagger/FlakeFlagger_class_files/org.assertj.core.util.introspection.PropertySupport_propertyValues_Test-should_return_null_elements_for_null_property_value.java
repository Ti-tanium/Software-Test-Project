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
package org.assertj.core.util.introspection;

import static java.util.Collections.emptySet;
import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import org.assertj.core.internal.PropertySupport;
import org.assertj.core.test.Employee;
import org.assertj.core.test.ExpectedException;
import org.assertj.core.test.Name;
import org.assertj.core.test.VehicleFactory;
import org.assertj.core.util.introspection.IntrospectionError;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for <code>{@link PropertySupport#propertyValues(String, Collection)}</code>.
 * 
 * @author Yvonne Wang
 * @author Nicolas François
 * @author Mikhail Mazursky
 * @author Florent Biville
 */
public class PropertySupport_propertyValues_Test {

  private Employee yoda;
  private Employee luke;
  private Iterable<Employee> employees;

  @Before
  public void setUpOnce() {
    yoda = new Employee(6000L, new Name("Yoda"), 800);
    luke = new Employee(8000L, new Name("Luke", "Skywalker"), 26);
    employees = newArrayList(yoda, luke);
  }

  @Rule
  public ExpectedException thrown = none();

  @Test public void should_return_null_elements_for_null_property_value(){List<Employee> list=newArrayList(null,null);Iterable<Integer> ages=PropertySupport.instance().propertyValues("ages",Integer.class,list);assertThat(ages).containsExactly(null,null);list=newArrayList(yoda,luke,null,null);ages=PropertySupport.instance().propertyValues("age",Integer.class,list);assertThat(ages).containsExactly(800,26,null,null);}

}
