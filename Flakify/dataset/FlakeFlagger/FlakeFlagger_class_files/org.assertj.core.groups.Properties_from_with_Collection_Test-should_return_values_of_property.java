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
package org.assertj.core.groups;

import static junit.framework.Assert.assertSame;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.*;

import java.util.*;


import org.assertj.core.groups.Properties;
import org.assertj.core.internal.PropertySupport;
import org.assertj.core.test.*;
import org.junit.*;

/**
 * Tests for <code>{@link Properties#from(Collection)}</code>.
 * 
 * @author Yvonne Wang
 * @author Mikhail Mazursky
 */
public class Properties_from_with_Collection_Test {

  private static Employee yoda;
  private static List<Employee> employees;

  @BeforeClass
  public static void setUpOnce() {
    yoda = new Employee(6000L, new Name("Yoda"), 800);
    employees = newArrayList(yoda);
  }

  private PropertySupport propertySupport;
  private String propertyName;
  private Properties<Integer> properties;

  @Before
  public void setUp() {
    propertySupport = mock(PropertySupport.class);
    propertyName = "age";
    properties = new Properties<>(propertyName, Integer.class);
    properties.propertySupport = propertySupport;
  }

  @Test public void should_return_values_of_property(){List<Integer> ages=newArrayList();ages.add(yoda.getAge());when(propertySupport.propertyValues(propertyName,Integer.class,employees)).thenReturn(ages);assertSame(ages,properties.from(employees));}
}
